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

package io.bumble.slowdonkey.server.property;

import io.bumble.slowdonkey.common.property.AbstractSlowDonkeyPropertyHolder;
import io.bumble.slowdonkey.common.property.SlowDonkeyProperty;
import io.bumble.slowdonkey.common.util.SingletonUtil;

/**
 * Hold all the server properties.
 *
 * @author shenxiangyu on 2020/04/05
 */
public class SlowDonkeyServerPropertyHolder extends AbstractSlowDonkeyPropertyHolder {

    /**
     * The timeout config for leader waiting for the response of appending uncommitted log to followers
     */
    private SlowDonkeyProperty leaderAppendUncommittedLogToFollowersTimeout =
            SlowDonkeyProperty.builder().key("bumble.slow.donkey.leaderAppendUncommittedLogToFollowersTimeout")
                    .value("10000").build();

    /**
     * Thread pool size for sending data from leader to followers.
     */
    private SlowDonkeyProperty leaderDataToFollowerThreadPoolSize =
            SlowDonkeyProperty.builder().key("bumble.slow.donkey.leaderDataToFollowerThreadPoolSize")
                    .value("4").build();

    /**
     * Thread pool size for sending data from leader to observers.
     */
    private SlowDonkeyProperty leaderDataToObserverThreadPoolSize =
            SlowDonkeyProperty.builder().key("bumble.slow.donkey.leaderDataToObserverThreadPoolSize")
                    .value("4").build();

    /**
     * Thread pool queue capacity for sending data from leader to followers.
     */
    private SlowDonkeyProperty leaderDataToFollowerThreadPoolQueueCapacity =
            SlowDonkeyProperty.builder().key("bumble.slow.donkey.leaderDataToFollowerThreadPoolQueueCapacity")
                    .value("100000").build();

    /**
     * Thread pool queue capacity for sending data from leader to observers.
     */
    private SlowDonkeyProperty leaderDataToObserverThreadPoolQueueCapacity =
            SlowDonkeyProperty.builder().key("bumble.slow.donkey.leaderDataToObserverThreadPoolQueueCapacity")
                    .value("100000").build();

    /**
     * File directory for snapshot files.
     */
    private SlowDonkeyProperty snapshotFilePath =
            SlowDonkeyProperty.builder().key("bumble.slow.donkey.snapshotFilePath")
                    .value("/usr/local/var/slow-donkey/data/snapshot").build();

    /**
     * File directory for transaction logs.
     */
    private SlowDonkeyProperty txnLogFilePath =
            SlowDonkeyProperty.builder().key("bumble.slow.donkey.txnLogFilePath")
                    .value("/usr/local/var/slow-donkey/data/txn-log").build();

    public SlowDonkeyServerPropertyHolder() {
        super();
    }

    public static SlowDonkeyServerPropertyHolder getInstance() {
        return SingletonUtil.getInstance(SlowDonkeyServerPropertyHolder.class);
    }

    public void loadPropertiesFromFileAndRefresh() {

        // Load the property file and read each line to the property memory
        int lineCount = 10;
        for (int i = 0; i < lineCount; i++) {

            super.propertyMap.get("key").setValue("");
        }
    }

    public SlowDonkeyProperty getLeaderAppendUncommittedLogToFollowersTimeout() {
        return leaderAppendUncommittedLogToFollowersTimeout;
    }

    public SlowDonkeyProperty getLeaderDataToFollowerThreadPoolSize() {
        return leaderDataToFollowerThreadPoolSize;
    }

    public SlowDonkeyProperty getLeaderDataToObserverThreadPoolSize() {
        return leaderDataToObserverThreadPoolSize;
    }

    public SlowDonkeyProperty getLeaderDataToFollowerThreadPoolQueueCapacity() {
        return leaderDataToFollowerThreadPoolQueueCapacity;
    }

    public SlowDonkeyProperty getLeaderDataToObserverThreadPoolQueueCapacity() {
        return leaderDataToObserverThreadPoolQueueCapacity;
    }
}
