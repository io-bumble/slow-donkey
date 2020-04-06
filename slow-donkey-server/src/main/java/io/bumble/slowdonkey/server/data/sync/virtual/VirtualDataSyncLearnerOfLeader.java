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

package io.bumble.slowdonkey.server.data.sync.virtual;

import io.bumble.slowdonkey.common.model.Node;
import io.bumble.slowdonkey.common.remoting.TransportClient;
import io.bumble.slowdonkey.server.model.network.leader2oth.DataSyncRequest;
import io.bumble.slowdonkey.server.model.network.leader2oth.DataSyncResponse;
import io.bumble.slowdonkey.server.persistence.CommitLogEntry;
import io.bumble.slowdonkey.server.persistence.Offset;

/**
 * @author shenxiangyu on 2020/03/31
 */
public class VirtualDataSyncLearnerOfLeader extends Node {

    public Offset getCommitOffset() {


        return null;
    }

    public boolean sync(CommitLogEntry commitLogEntry) {

        // Send data sync request to TODO

        DataSyncResponse response = TransportClient.getInstance().syncRequest(new DataSyncRequest(commitLogEntry));

        return response.isSuccess();
    }
}
