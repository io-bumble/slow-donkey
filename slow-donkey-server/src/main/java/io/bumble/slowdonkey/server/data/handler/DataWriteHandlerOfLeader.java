/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.bumble.slowdonkey.server.data.handler;

import io.bumble.slowdonkey.common.model.network.client2server.WriteRequest;
import io.bumble.slowdonkey.common.model.network.client2server.WriteResponse;
import io.bumble.slowdonkey.common.thread.ThreadPoolExecutorGenerator;
import io.bumble.slowdonkey.common.util.SingletonUtil;
import io.bumble.slowdonkey.server.SlowDonkey;
import io.bumble.slowdonkey.server.data.SlowDonkeyDatabase;
import io.bumble.slowdonkey.server.model.network.leader2follower.CommitRequest;
import io.bumble.slowdonkey.server.model.network.leader2follower.ProposeRequest;
import io.bumble.slowdonkey.server.model.network.leader2follower.ProposeResponse;
import io.bumble.slowdonkey.server.property.SlowDonkeyServerPropertyHolder;
import io.bumble.slowdonkey.server.quorum.QuorumVerifier;
import io.bumble.slowdonkey.server.role.virtual.VirtualFollowerOfLeader;
import io.bumble.slowdonkey.server.role.virtual.VirtualObserverOfLeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * It is called by the leader role server node, and responsible for write data.
 *
 * @author shenxiangyu on 2020/04/05
 */
public class DataWriteHandlerOfLeader {

    private Logger logger = LoggerFactory.getLogger(DataWriteHandlerOfLeader.class);

    /**
     * Thread pool for leader sending append uncommitted log request and commit request to followers.
     */
    private ThreadPoolExecutor leaderDataToFollowerExecutor = ThreadPoolExecutorGenerator.generator(
            "leader-data-to-follower",
            SlowDonkeyServerPropertyHolder.getInstance().getLeaderDataToFollowerThreadPoolSize().getIntValue(),
            SlowDonkeyServerPropertyHolder.getInstance().getLeaderDataToFollowerThreadPoolQueueCapacity().getIntValue());

    /**
     * Thread pool for leader sync data to observers.
     */
    private ThreadPoolExecutor leaderDataToObserverExecutor = ThreadPoolExecutorGenerator.generator(
            "leader-data-to-observer",
            SlowDonkeyServerPropertyHolder.getInstance().getLeaderDataToObserverThreadPoolSize().getIntValue(),
            SlowDonkeyServerPropertyHolder.getInstance().getLeaderDataToObserverThreadPoolQueueCapacity().getIntValue());

    public static DataWriteHandlerOfLeader getInstance() {
        return SingletonUtil.getInstance(DataWriteHandlerOfLeader.class);
    }

    /**
     * Write data.
     * Append uncommitted transaction log to all the followers.
     * If half of the followers append the uncommitted transaction log successfully, then send commit request to all
     * the followers.
     *
     * @param request write data request
     * @param followerList followers
     * @param observerList observers
     * @return write data response
     */
    public WriteResponse write(WriteRequest request,
                               List<VirtualFollowerOfLeader> followerList,
                               List<VirtualObserverOfLeader> observerList) {
        String errMsg;

        // 1. Append uncommitted transaction log to the data tree and flush to the transaction log.
        if (!SlowDonkeyDatabase.getInstance().propose(request)) {
            errMsg = "Leader append uncommitted transaction log failed";
            logger.error(errMsg);
            return WriteResponse.renderResponse(false, errMsg);
        }

        // 2. Send propose request to all the followers.
        //    Thread waits until quorum of followers response successfully.
        boolean proposeToFollowersSuccess = proposeToFollowers(request, followerList);
        if (proposeToFollowersSuccess) {

            // 3. Send commit request to all the followers asynchronously.
            asyncCommitToFollowers(request, followerList);

            // 4. Commit the request
            boolean commitSuccess = SlowDonkeyDatabase.getInstance().commit(request);
            if (!commitSuccess) {
                logger.error("Stop leading, wait for new leader.");

                SlowDonkey.getInstance().stop();

                return WriteResponse.renderResponse(true);
            }

            // 5. Send data replicate request to all the observers asynchronously.
            asyncDataReplicateToAllObservers(observerList);

            // 6. Send write success ack response to the client.
            return WriteResponse.renderResponse(true);

        } else {
            errMsg = "Followers append uncommitted transaction log failed";
            logger.error(errMsg);

            // 3. Send write fail ack response to the client.
            return WriteResponse.renderResponse(false, errMsg);
        }
    }

    /**
     * Append uncommitted transaction log to all the followers
     * <pre>
     * 1. Send write request to all the followers asynchronously.
     * 2. Main thread waits for a timeout period with a lock condition.
     * 3. A quorum of success requests will awake the awaiting main thread or
     *    A quorum of fail requests will also awake the awaiting main thread.
     *
     *    (Quorum of requests means more than half of the requests)
     * </pre>
     *
     * @param request write data request
     * @param followerList follower list on the leader's perspective
     */
    private boolean proposeToFollowers(WriteRequest request, List<VirtualFollowerOfLeader> followerList) {

        int totalCount = followerList.size();

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        lock.lock();
        for (VirtualFollowerOfLeader follower : followerList) {

            leaderDataToFollowerExecutor.execute(() -> {

                ProposeResponse response = follower.propose(ProposeRequest.fromWriteRequest(request));
                if (response.isSuccess()) {
                    successCount.incrementAndGet();
                } else {
                    failCount.incrementAndGet();
                }
                if (QuorumVerifier.verify(totalCount, successCount.get()) ||
                    QuorumVerifier.verify(totalCount, failCount.get())) {
                    condition.signal();
                }
            });
        }

        try {
            condition.await(
                    SlowDonkeyServerPropertyHolder.getInstance()
                            .getLeaderAppendUncommittedLogToFollowersTimeout().getIntValue(),
                    TimeUnit.MILLISECONDS);
            return QuorumVerifier.verify(totalCount, successCount.get());
        } catch (InterruptedException e) {
            logger.error("Append uncommitted log to followers awaiting thread is interrupted");
            Thread.currentThread().interrupt();
            return QuorumVerifier.verify(totalCount, successCount.get());
        } finally {
            lock.unlock();
        }
    }

    private void asyncCommitToFollowers(WriteRequest request, List<VirtualFollowerOfLeader> followerList) {
        for (VirtualFollowerOfLeader follower : followerList) {
            leaderDataToFollowerExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    follower.commit(CommitRequest.fromWriteRequest(request));
                }
            });
        }
    }

    private void asyncDataReplicateToAllObservers(List<VirtualObserverOfLeader> observerList) {
        for (VirtualObserverOfLeader observer : observerList) {
            leaderDataToObserverExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    observer.dataSync();
                }
            });
        }
    }
}
