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
     * If this request need to be redirected, then a new request will be created with its parent request pointing to
     * this request.
     */
    private Request parentRequest;

    /**
     * Indicating the request direction
     */
    private RequestDirectionEnum requestDirectionEnum;

    public Request() {}

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

    public Request getParentRequest() {
        return parentRequest;
    }

    public void setParentRequest(Request parentRequest) {
        this.parentRequest = parentRequest;
    }

    public RequestDirectionEnum getRequestDirectionEnum() {
        return requestDirectionEnum;
    }

    public void setRequestDirectionEnum(RequestDirectionEnum requestDirectionEnum) {
        this.requestDirectionEnum = requestDirectionEnum;
    }
}
