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

package io.bumble.slowdonkey.server.role;

import io.bumble.slowdonkey.common.model.network.base.Request;
import io.bumble.slowdonkey.common.model.network.base.Response;
import io.bumble.slowdonkey.common.model.network.client2server.WriteRequest;
import io.bumble.slowdonkey.server.LifeCycle;
import io.bumble.slowdonkey.server.data.handler.DataWriteHandlerOfLeader;
import io.bumble.slowdonkey.server.model.Vote;
import io.bumble.slowdonkey.server.role.virtual.VirtualFollowerOfLeader;
import io.bumble.slowdonkey.server.role.virtual.VirtualObserverOfLeader;

import java.util.List;

/**
 * Responsible for acting as a leader node
 *
 * @author shenxiangyu on 2020/03/30
 */
@SuppressWarnings("unchecked")
public class Leader implements Role, Voter, LifeCycle {

    private List<VirtualFollowerOfLeader> followerList;

    private List<VirtualObserverOfLeader> observerList;

    @Override
    public <T extends Request, R extends Response> R receiveRequest(T request) {

        if (request instanceof WriteRequest) {
            return (R) DataWriteHandlerOfLeader.getInstance().write((WriteRequest) request, followerList, observerList);
        }
        return null;
    }

    @Override
    public Vote responseForVote(Vote vote) {

        // Return a vote for request from a candidate asking for a vote.

        // A comparison of last transaction id will be taken between the leader itself and the incoming candidate,
        // the one with greater last transaction id will win the election.

        return null;
    }

    @Override
    public void start() {

        // Synchronize the data to all the node, the synchronization will keep on retry if the synchronize response
        // failed or timed out.

        // Send heart beat request to all the node within a fix period interval.
    }

    @Override
    public void stop() {

    }
}
