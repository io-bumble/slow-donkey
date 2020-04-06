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
     * Send from the client to the server leader
     */
    CLIENT_TO_SERVER_LEADER,

    /**
     * Send from the client to all the server nodes
     */
    CLIENT_TO_SERVER_ALL,

    /**
     * Send from the leader to all the other server nodes
     */
    SERVER_LEADER_TO_SERVER_OTHER,

    /**
     * Send from the server other nodes to leader
     */
    SERVER_OTHER_TO_SERVER_LEADER,

    /**
     * Send from leader to followers
     */
    SERVER_LEADER_TO_SEVER_FOLLOWER,

    /**
     * Send from the server candidate to all the nodes except the observers
     */
    ELECTION,
    ;
}
