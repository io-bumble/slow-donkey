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

import io.bumble.slowdonkey.server.SlowDonkey;
import io.bumble.slowdonkey.server.LifeCycle;
import io.bumble.slowdonkey.server.model.Vote;
import io.bumble.slowdonkey.server.role.virtual.LeaderOfFollower;

/**
 * Follower is the default role of a machine node.
 *
 * @author shenxiangyu on 2020/03/30
 */
public class Follower implements Learner, Voter, LifeCycle {

    private LeaderOfFollower leader;

    @Override
    public void receiveRequest() {

        // If it is a heart beat request from leader, then reset the countdown timer for turning to candidate.

        // If it is a data sync request from leader, then a data sync process will be launched, and TODO

        // If it is a proposal write request from leader, then append the record to the uncommitted log and ack a
        // successful response back to the leader.

        // If it is a commit write request from leader, then change the uncommitted record to committed state.


    }

    @Override
    public Vote responseForVote(Vote vote) {

        // Return a vote for request from a candidate asking for a vote.
        // If there is no current leader, a vote for the incoming candidate will be returned.

        // If there is an existing leader, then a comparison of epoch will be taken between the existing leader and the
        // incoming candidate, the one with newer epoch (with greater num) will obtain the vote.

        return null;
    }

    @Override
    public void start() {

        // Handle heart beat request from leader, reset the timer. The follower will turn to a candidate when the timer
        // countdown is over.
        SlowDonkey.getInstance().follower2Candidate();

    }

    @Override
    public void stop() {

    }

}
