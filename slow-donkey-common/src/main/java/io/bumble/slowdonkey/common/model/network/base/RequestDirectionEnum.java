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

package io.bumble.slowdonkey.common.model.network.base;

/**
 * Indicating the request direction, make request clear of showing the original
 * request sender and the destination handler
 *
 * @author shenxiangyu on 2020/04/05
 */
public enum RequestDirectionEnum {

    /**
     * Client sends request to the server leader. If the request is sent to a server node
     * which is not a leader, then the request will be redirected to the leader.
     */
    CLIENT_TO_SERVER_LEADER,

    /**
     * Client sends request to any one of the server nodes regardless its role.
     */
    CLIENT_TO_SERVER_ANY,

    /**
     * Server leader sends request to server follower.
     */
    SERVER_LEADER_TO_SEVER_FOLLOWER,

    /**
     * Server leader sends request to server observer.
     */
    SERVER_LEADER_TO_SERVER_OBSERVER,

    /**
     * Server learner sends request to server leader.
     */
    SERVER_LEARNER_TO_SERVER_LEADER,

    /**
     * Sever candidate sends request to all the server nodes except the observers.
     */
    ELECTION,
    ;
}
