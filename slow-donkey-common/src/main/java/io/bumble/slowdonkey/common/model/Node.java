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
 * Represent a real machine in the cluster as a node
 *
 * @author shenxiangyu on 2020/03/31
 */
public class Node {

    /**
     * The ip address and port
     */
    private Host host;

    public Node() {}

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    /**
     * IP address and port
     */
    public class Host {

        private String ip;

        private int port;

        /**
         * Construct the ip and port with ':' symbol, e.g. 192.168.0.1:8080
         */
        private String value;

        public Host(String ip, int port) {
            this.ip = ip;
            this.port = port;
            this.value = ip + ":" + port;
        }

        /**
         * Constructor asking for a host value like 192.168.0.1:8080, split the string with ':' symbol and get the ip
         * address and port
         *
         * @param value host string constructed by join the ip address and port with ':' symbol
         */
        public Host(String value) {
            this.value = value;

            String[] arr = value.split(":");
            this.ip = arr[0];
            this.port = Integer.valueOf(arr[1]);
        }

        @Override
        public String toString() {
            return value;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
