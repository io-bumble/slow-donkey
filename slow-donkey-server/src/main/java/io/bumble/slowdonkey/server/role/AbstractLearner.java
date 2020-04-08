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
import io.bumble.slowdonkey.common.model.network.base.RequestDirectionEnum;
import io.bumble.slowdonkey.common.model.network.base.Response;
import io.bumble.slowdonkey.server.role.virtual.VirtualLeaderOfLearner;

/**
 * @author shenxiangyu on 2020/04/04
 */
public abstract class AbstractLearner implements Role {

    protected VirtualLeaderOfLearner virtualLeaderOfLearner;

    protected boolean syncedWithLeader;

    @Override
    public <T extends Request, R extends Response> R receiveRequest(T request) {

        if (RequestDirectionEnum.CLIENT_TO_SERVER_LEADER.equals(request.getRequestDirectionEnum())) {

            // Redirect the request to the leader if the request destination is leader
            return virtualLeaderOfLearner.redirectRequestToLeader(request);
        }

        return doReceiveRequest(request);
    }

    abstract protected <T extends Request, R extends Response> R doReceiveRequest(T request);
}
