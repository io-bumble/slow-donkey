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

package io.bumble.slowdonkey.server.model.network.learner2leader;

import io.bumble.slowdonkey.common.model.network.base.Request;
import io.bumble.slowdonkey.common.model.network.base.RequestDirectionEnum;

/**
 * A server node which is not leader sends request to leader to get leader snapshot data.
 * If there is an existing snapshot on this server, pass the md5 to the leader, and the leader will verify the incoming
 * md5 and the md5 of leader snapshot, if they are different the leader snapshot will be returned, otherwise an identical
 * flag will be returned.
 *
 * @author shenxiangyu on 2020/04/08
 */
public class SnapshotReplicateRequest extends Request {

    private String snapshotMd5;

    public SnapshotReplicateRequest(String snapshotMd5) {
        super.setRequestDirectionEnum(RequestDirectionEnum.SERVER_LEARNER_TO_SERVER_LEADER);

        this.snapshotMd5 = snapshotMd5;
    }

    public String getSnapshotMd5() {
        return snapshotMd5;
    }

    public void setSnapshotMd5(String snapshotMd5) {
        this.snapshotMd5 = snapshotMd5;
    }
}
