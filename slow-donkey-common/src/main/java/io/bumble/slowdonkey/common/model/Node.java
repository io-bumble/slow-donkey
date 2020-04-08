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

package io.bumble.slowdonkey.common.model;

/**
 * Represent a machine in the cluster as a node
 *
 * @author shenxiangyu on 2020/03/31
 */
public class Node {

    /**
     * The ip address and port
     */
    private Endpoint endpoint;

    public Node() {}

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * IP address and port
     */
    public class Endpoint {

        private String value;

        private String ip;

        private int port;

        public Endpoint(String endpoint) {
            this.value = endpoint;

            String[] arr = endpoint.split(":");
            this.ip = arr[0];
            this.port = Integer.valueOf(arr[1]);
        }

        public Endpoint(String ip, int port) {
            this.ip = ip;
            this.port = port;

            this.value = ip + ":" + port;
        }

        @Override
        public String toString() {
            return value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
