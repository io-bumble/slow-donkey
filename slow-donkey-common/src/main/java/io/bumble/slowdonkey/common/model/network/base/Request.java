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

import java.util.Queue;

/**
 *
 * @author shenxiangyu on 2020/03/31
 */
public class Request {

    private Node.Host orgClientHost;

    private Node.Host orgServerHost;

    private Node.Host clientHost;

    private Node.Host serverHost;

    private Queue<Node.Host> requestCallStack;

    /**
     * Indicating the request direction
     */
    private RequestDirectionEnum requestDirectionEnum;

    public Request() {}

    public Node.Host getOrgClientHost() {
        return orgClientHost;
    }

    public void setOrgClientHost(Node.Host orgClientHost) {
        this.orgClientHost = orgClientHost;
    }

    public Node.Host getOrgServerHost() {
        return orgServerHost;
    }

    public void setOrgServerHost(Node.Host orgServerHost) {
        this.orgServerHost = orgServerHost;
    }

    public Node.Host getClientHost() {
        return clientHost;
    }

    public void setClientHost(Node.Host clientHost) {
        this.clientHost = clientHost;
    }

    public Node.Host getServerHost() {
        return serverHost;
    }

    public void setServerHost(Node.Host serverHost) {
        this.serverHost = serverHost;
    }

    public Queue<Node.Host> getRequestCallStack() {
        return requestCallStack;
    }

    public void setRequestCallStack(Queue<Node.Host> requestCallStack) {
        this.requestCallStack = requestCallStack;
    }

    public RequestDirectionEnum getRequestDirectionEnum() {
        return requestDirectionEnum;
    }

    public void setRequestDirectionEnum(RequestDirectionEnum requestDirectionEnum) {
        this.requestDirectionEnum = requestDirectionEnum;
    }
}
