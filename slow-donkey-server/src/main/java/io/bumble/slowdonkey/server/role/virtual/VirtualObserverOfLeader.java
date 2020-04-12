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

package io.bumble.slowdonkey.server.role.virtual;

import io.bumble.slowdonkey.common.model.Node;
import io.bumble.slowdonkey.common.remoting.TransportClient;
import io.bumble.slowdonkey.server.model.network.leader2observer.DataSyncRequest;
import io.bumble.slowdonkey.server.model.network.leader2observer.DataSyncResponse;
import io.bumble.slowdonkey.server.persistence.TxnLogEntry;

/**
 * @author shenxiangyu on 2020/04/05
 */
public class VirtualObserverOfLeader extends Node implements VirtualRole {

    public void dataSync() {

    }

    public long getCommitOffset() {

        return 0;
    }

    public boolean sync(TxnLogEntry txnLogEntry) {

        DataSyncResponse response = TransportClient.getInstance().syncRequest(new DataSyncRequest());

        return response.isSuccess();
    }
}
