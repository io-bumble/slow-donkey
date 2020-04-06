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

package io.bumble.slowdonkey.server.data.write;

import io.bumble.slowdonkey.common.model.network.client2server.WriteRequest;
import io.bumble.slowdonkey.common.model.network.client2server.WriteResponse;
import io.bumble.slowdonkey.common.util.SingletonUtil;
import io.bumble.slowdonkey.server.data.DataTree;
import io.bumble.slowdonkey.server.role.virtual.VirtualFollowerOfLeader;
import io.bumble.slowdonkey.server.role.virtual.VirtualObserverOfLeader;

import java.util.List;

/**
 * @author shenxiangyu on 2020/04/05
 */
public class DataWriteHandlerOfLeader {

    public static DataWriteHandlerOfLeader getInstance() {
        return SingletonUtil.getInstance(DataWriteHandlerOfLeader.class);
    }

    public WriteResponse write(WriteRequest request,
                               List<VirtualFollowerOfLeader> followerList,
                               List<VirtualObserverOfLeader> observerList) {

        // TODO

        // Append uncommitted log to all the followers
        // If half of the followers append the uncommitted log successfully, then send commit request to all the followers.

        // 1. Add uncommitted log to the data tree and flush to the commit log
        DataTree.getInstance().writeUncommittedLog(request);

        // 2. Send append uncommitted log request to all the followers
        appendUncommittedLogToFollowers(request, followerList);

        // 3. Send ack response to the client if more than half followers append uncommitted log successfully


        // 4. Send sync request to all the observers asynchronously.

        return null;
    }

    private void appendUncommittedLogToFollowers(WriteRequest request,
                                                 List<VirtualFollowerOfLeader> followerList) {

    }

    private void commitToFollowers(WriteRequest request,
                                   List<VirtualFollowerOfLeader> followerList) {

    }

    private void syncToAllObservers(WriteRequest request,
                                    List<VirtualFollowerOfLeader> observerList) {

    }
}
