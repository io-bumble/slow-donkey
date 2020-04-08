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

import io.bumble.slowdonkey.common.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * The basic network request model.
 *
 * @author shenxiangyu on 2020/03/31
 */
public class Request {

    /**
     * The client side endpoint.
     */
    private Node.Endpoint clientEndpoint;

    /**
     * The server side endpoint.
     */
    private Node.Endpoint serverEndpoint;

    /**
     * <pre>
     * 1. If the request is called from client1 endpoint1 to server1 endpoint2
     *      client1 : callStack is [endpoint1, endpoint2]
     *      server1 : callStack is [endpoint1, endpoint2]
     *
     * 2. If the request is called from client1 endpoint1 to server endpoint2 and redirected to server endpoint3
     *      client1 : callStack is [endpoint1, endpoint2]
     *      server1 : callStack is [endpoint1, endpoint2]
     *                        |
     *                        | changed
     *                        v
     *                callStack is [endpoint1, endpoint2, endpoint3]
     *      server2 : callStack is [endpoint1, endpoint2, endpoint3]
     * </pre>
     */
    private List<Node.Endpoint> callStack = new ArrayList<>();

    /**
     * Indicating the request direction
     */
    private RequestDirectionEnum requestDirectionEnum;

    public Request() {}

    public Request(Node.Endpoint clientEndpoint, Node.Endpoint serverEndpoint) {
        this.setClientEndpoint(clientEndpoint);
        this.setServerEndpoint(serverEndpoint);

        callStack.add(clientEndpoint);
        callStack.add(serverEndpoint);
    }

    public void redirectServerEndpoint(Node.Endpoint endpoint) {
        this.setServerEndpoint(endpoint);
        callStack.add(endpoint);
    }

    public Node.Endpoint getClientEndpoint() {
        return clientEndpoint;
    }

    public void setClientEndpoint(Node.Endpoint clientEndpoint) {
        this.clientEndpoint = clientEndpoint;
    }

    public Node.Endpoint getServerEndpoint() {
        return serverEndpoint;
    }

    public void setServerEndpoint(Node.Endpoint serverEndpoint) {
        this.serverEndpoint = serverEndpoint;
    }

    public List<Node.Endpoint> getCallStack() {
        return callStack;
    }

    public void setCallStack(List<Node.Endpoint> callStack) {
        this.callStack = callStack;
    }

    public RequestDirectionEnum getRequestDirectionEnum() {
        return requestDirectionEnum;
    }

    public void setRequestDirectionEnum(RequestDirectionEnum requestDirectionEnum) {
        this.requestDirectionEnum = requestDirectionEnum;
    }
}
