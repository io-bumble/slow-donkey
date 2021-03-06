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

package io.bumble.slowdonkey.common.model.network.client2server;

import io.bumble.slowdonkey.common.model.network.base.Request;
import io.bumble.slowdonkey.common.model.network.base.RequestDirectionEnum;

/**
 * Client read data from leader, if a server node which is not a leader receives this request then the request will be
 * redirected to the server leader to read the data.
 *
 * @author shenxiangyu on 2020/04/05
 */
public class StrictReadRequest extends Request {

    public StrictReadRequest() {
        super.setRequestDirectionEnum(RequestDirectionEnum.CLIENT_TO_SERVER_LEADER);
    }
}
