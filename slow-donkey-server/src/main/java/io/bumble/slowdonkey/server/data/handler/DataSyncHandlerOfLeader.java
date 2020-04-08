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

import io.bumble.slowdonkey.common.util.SingletonUtil;
import io.bumble.slowdonkey.server.persistence.TxnLogEntry;
import io.bumble.slowdonkey.server.role.virtual.VirtualObserverOfLeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Handle data sync from leader to observers.
 * The data will be synced to the observers after the data mutation has been done by the leader.
 *
 * @author shenxiangyu on 2020/03/31
 */
public class DataSyncHandlerOfLeader {

    private Logger logger = LoggerFactory.getLogger(DataSyncHandlerOfLeader.class);

    private Map<String, VirtualObserverOfLeader> observerOfLeaderMap;

    public static DataSyncHandlerOfLeader getInstance() {
        return SingletonUtil.getInstance(DataSyncHandlerOfLeader.class);
    }

    /**
     * Sync data to all observers.
     */
    public void syncToAllObservers(List<TxnLogEntry> txnLogEntries) {
        for (Map.Entry<String, VirtualObserverOfLeader> entry : observerOfLeaderMap.entrySet()) {
            syncToObserver(entry.getKey(), txnLogEntries);
        }
    }

    public void syncToObserver(String endpoint, List<TxnLogEntry> txnLogEntries) {
        VirtualObserverOfLeader observer = observerOfLeaderMap.get(endpoint);
        if (observer == null) {
            observer = observerOfLeaderMap.get(endpoint);
        }
        if (observer == null) {
            logger.error("[Data Sync] observer [{}] not found", endpoint);
            return;
        }
        syncToObserver(observer, txnLogEntries);
    }

    public void syncToObserver(VirtualObserverOfLeader observer, List<TxnLogEntry> txnLogEntries) {

        for (TxnLogEntry txnLogEntry : txnLogEntries) {
            boolean syncSuccess = observer.sync(txnLogEntry);
            if (!syncSuccess) {
                logger.error("[Data Sync] sync to observer [{}] failed", observer.getEndpoint().getValue());
                return;
            }
        }
    }
}
