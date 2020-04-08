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

package io.bumble.slowdonkey.server;

import io.bumble.slowdonkey.common.model.Node;
import io.bumble.slowdonkey.common.model.network.base.Request;
import io.bumble.slowdonkey.common.model.network.base.Response;
import io.bumble.slowdonkey.common.model.network.client2server.StrictReadRequest;
import io.bumble.slowdonkey.common.util.SingletonUtil;
import io.bumble.slowdonkey.server.role.Candidate;
import io.bumble.slowdonkey.server.role.Follower;
import io.bumble.slowdonkey.server.role.Leader;
import io.bumble.slowdonkey.server.role.Role;

/**
 * This represents for the machine node itself, and the role will be changed among FOLLOWER, CANDIDATE and LEADER.
 * <pre>
 *     The following is the role change direction
 *     follower -> candidate -> leader
 *       ^  ^           |         |
 *       |  |           v         |
 *       |  `-----------          v
 *       `------------------------
 *
 *     Observer role is a delicate role fixed right there for supporting high concurrent read.
 *     The high concurrent read has weakness that the data read may be an out of date data.
 *     Please refer to {@link StrictReadRequest} for details.
 * </pre>
 *
 * @author shenxiangyu on 2020/03/30
 */
public class SlowDonkey extends Node implements LifeCycle {

    public static SlowDonkey getInstance() {
        return SingletonUtil.getInstance(SlowDonkey.class);
    }

    private Follower follower = new Follower();

    private Candidate candidate = new Candidate();

    private Leader leader = new Leader();

    private Role currentRole = follower;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public <T extends Request, R extends Response> R receiveRequest(T request) {
        return currentRole.receiveRequest(request);
    }

    public Role getCurrentRole() {
        return currentRole;
    }

    public void follower2Candidate() {
        follower.stop();

        candidate.start();

        currentRole = candidate;
    }

    public void candidate2Follower() {
        candidate.stop();

        follower.start();

        currentRole = follower;
    }

    public void candidate2Leader() {
        candidate.stop();

        leader.start();

        currentRole = leader;
    }

    public void leader2Follower() {
        leader.stop();

        follower.start();

        currentRole = follower;
    }
}
