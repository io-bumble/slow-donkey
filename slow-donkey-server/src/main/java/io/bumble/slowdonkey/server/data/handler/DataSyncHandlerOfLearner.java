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

import io.bumble.slowdonkey.common.remoting.TransportClient;
import io.bumble.slowdonkey.common.util.SingletonUtil;
import io.bumble.slowdonkey.server.data.DataTree;
import io.bumble.slowdonkey.server.model.network.learner2leader.SnapshotReplicateRequest;
import io.bumble.slowdonkey.server.model.network.learner2leader.SnapshotReplicateResponse;
import io.bumble.slowdonkey.server.model.network.learner2leader.TxnLogSyncRequest;
import io.bumble.slowdonkey.server.model.network.learner2leader.TxnLogSyncResponse;
import io.bumble.slowdonkey.server.persistence.TxnLog;

/**
 * The learner will sync data from the leader. A learner can be a follower or an observer.
 * When a follower or an observer starts up the sync process will be launched.
 * When a new leader takes place, the sync process will be launched.
 *
 * @author shenxiangyu on 2020/03/31
 */
public class DataSyncHandlerOfLearner {

    public static DataSyncHandlerOfLearner getInstance() {
        return SingletonUtil.getInstance(DataSyncHandlerOfLearner.class);
    }

    /**
     * Sync data from leader.
     * <ul>
     *     <li>Sync snapshot.</li>
     *     <li>Sync transaction logs.</li>
     * </ul>
     *
     * @return true if success
     */
    public boolean sync() {

        // Get snapshot replication from the leader.
        SnapshotReplicateResponse snapshotReplicateResponse = TransportClient.getInstance().retrySyncRequest(
                new SnapshotReplicateRequest(DataTree.getInstance().getSnapshot().getMd5()));

        if (!snapshotReplicateResponse.isSuccess()) {
            return false;
        }
        if (!snapshotReplicateResponse.isIdentical()) {
            DataTree.getInstance().getSnapshot().deserialize(snapshotReplicateResponse.getData());
        }

        // Get transaction logs from the last transaction id in the snapshot.
        TxnLogSyncResponse txnLogSyncResponse = TransportClient.getInstance().retrySyncRequest(
                new TxnLogSyncRequest(DataTree.getInstance().getSnapshot().getLastTxnId()));

        // Append all the transaction logs to the data tree.
        if (txnLogSyncResponse.isSuccess()) {
            for (TxnLog txnLog : txnLogSyncResponse.getTxnLogList()) {
                boolean appendSuccess = DataTree.getInstance().appendTxnLog(txnLog);
                if (!appendSuccess) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
