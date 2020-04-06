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

package io.bumble.slowdonkey.server.data.sync;

import io.bumble.slowdonkey.common.model.Node;
import io.bumble.slowdonkey.common.util.SingletonUtil;
import io.bumble.slowdonkey.server.data.sync.virtual.VirtualDataSyncLearnerOfLeader;
import io.bumble.slowdonkey.server.data.write.DataWriteHandlerOfLeader;
import io.bumble.slowdonkey.server.persistence.CommitLog;
import io.bumble.slowdonkey.server.persistence.CommitLogEntry;
import io.bumble.slowdonkey.server.persistence.Offset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Handle data sync from leader to learners
 *
 * @author shenxiangyu on 2020/03/31
 */
public class DataSyncHandlerOfLeader extends Node {

    private Logger logger = LoggerFactory.getLogger(DataSyncHandlerOfLeader.class);

    private Map<String, VirtualDataSyncLearnerOfLeader> learnerOfLeaderMap;

    private DataWriteHandlerOfLeader dataWriteHandlerOfLeader;

    public static DataSyncHandlerOfLeader getInstance() {
        return SingletonUtil.getInstance(DataSyncHandlerOfLeader.class);
    }

    /**
     * Sync data to all learners
     */
    public void syncToAllLearners() {
        for (Map.Entry<String, VirtualDataSyncLearnerOfLeader> entry : learnerOfLeaderMap.entrySet()) {
            syncToLearner(entry.getKey());
        }
    }

    public void syncToLearner(String endpoint) {
        VirtualDataSyncLearnerOfLeader learner = learnerOfLeaderMap.get(endpoint);
        if (learner == null) {
            learner = learnerOfLeaderMap.get(endpoint);
        }
        if (learner == null) {
            logger.error("[Data Sync] slave [{}] not found", endpoint);
            return;
        }
        syncToLearner(learner);
    }

    public void syncToLearner(VirtualDataSyncLearnerOfLeader learner) {
        Offset offset = learner.getCommitOffset();
        if (offset == null) {
            logger.error("[Data Sync] get slave [{}] commit offset failed", learner.getEndpoint());
            return;
        }

        // Get commit log entries which should be synchronized to this slave
        List<CommitLogEntry> commitLogEntries = CommitLog.getInstance().getCommittedEntryListFromOffset(offset);

        for (CommitLogEntry commitLogEntry : commitLogEntries) {
            boolean syncSuccess = learner.sync(commitLogEntry);
            if (!syncSuccess) {
                logger.error("[Data Sync] sync to slave [{}] failed", learner.getEndpoint());
                return;
            }
        }
    }
}
