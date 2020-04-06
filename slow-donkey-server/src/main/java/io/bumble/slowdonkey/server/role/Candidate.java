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
import io.bumble.slowdonkey.server.SlowDonkey;
import io.bumble.slowdonkey.server.LifeCycle;
import io.bumble.slowdonkey.server.model.Vote;

/**
 * This is a role standing between the Leader and Follower.
 * <pre>
 * When the follower looses the heart beat from leader for a timeout period, this follower will change its role from
 * follower to candidate and send request asking for a vote to itself expecting to be a new leader to all the nodes.
 *
 * When a leader looses network for a period, then a new leader comes out. The old leader will receive heart beat
 * request from the new leader with a newer epoch, and the old leader will realize the situation and becomes to a
 * follower.
 *
 * </pre>
 *
 * @author shenxiangyu on 2020/03/30
 */
public class Candidate extends AbstractLearner implements Voter, LifeCycle {

    @Override
    public Vote responseForVote(Vote vote) {

        // Return a vote for request from a candidate asking for a vote.

        // A comparison of epoch will be taken between the candidate itself and the incoming candidate, the one with
        // newer epoch (with greater num) will obtain the vote.

        // If a vote request sent from another candidate with a newer epoch arrived, then this node role will be
        // changed from candidate to follower.

        SlowDonkey.getInstance().candidate2Follower();

        return null;
    }

    @Override
    public void start() {

        // Send vote request with a proposal voting to itself to all the node asking for a vote return.
        // If more than half of the cluster accept the vote proposal, then this candidate becomes the new leader.
        SlowDonkey.getInstance().candidate2Leader();

    }

    @Override
    public void stop() {

    }

    @Override
    protected <T extends Request, R extends Response> R doReceiveRequest(T request) {
        return null;
    }

}
