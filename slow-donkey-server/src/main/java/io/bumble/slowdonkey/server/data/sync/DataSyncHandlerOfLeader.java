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
import io.bumble.slowdonkey.server.data.sync.virtual.DataSyncLearnerOfLeader;
import io.bumble.slowdonkey.server.persistence.CommitLog;
import io.bumble.slowdonkey.server.persistence.CommitLogEntry;
import io.bumble.slowdonkey.server.persistence.Offset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 *
 * @author shenxiangyu on 2020/03/31
 */
public class DataSyncHandlerOfLeader extends Node {

    private Logger logger = LoggerFactory.getLogger(DataSyncHandlerOfLeader.class);

    private Map<String, DataSyncLearnerOfLeader> learnerOfLeaderMap;

    public static DataSyncHandlerOfLeader getInstance() {
        return SingletonUtil.getInstance(DataSyncHandlerOfLeader.class);
    }

    public void syncToAllLearners() {

        for (Map.Entry<String, DataSyncLearnerOfLeader> entry : learnerOfLeaderMap.entrySet()) {
            syncToLearner(entry.getKey());
        }
    }

    public void syncToLearner(String learnerHost) {
        DataSyncLearnerOfLeader learner = learnerOfLeaderMap.get(learnerHost);
        if (learner == null) {
            logger.error("[Data Sync] slave not found from master [] to slave [{}]", super.getHost(), learnerHost);
            return;
        }

        Offset offset = learner.getCommitOffset();
        if (offset == null) {
            logger.error("[Data Sync] get commit offset failed from master [] to slave [{}]", super.getHost(), learner.getHost());
            return;
        }

        // Get commit log entries which should be synchronized to this slave
        List<CommitLogEntry> commitLogEntries = CommitLog.getInstance().getCommittedEntryListFromOffset(offset);

        boolean syncSuccessForThisSlave = true;
        for (CommitLogEntry commitLogEntry : commitLogEntries) {
            syncSuccessForThisSlave = learner.sync(commitLogEntry);
            if (!syncSuccessForThisSlave) {
                break;
            }
        }
        if (!syncSuccessForThisSlave) {
            logger.error("[Data Sync] sync failed from master [] to slave [{}]", super.getHost(), learner.getHost());
        }
    }
}
