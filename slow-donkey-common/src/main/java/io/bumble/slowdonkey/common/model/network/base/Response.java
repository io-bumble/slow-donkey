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
 * @author shenxiangyu on 2020/03/31
 */
public class Response {

    private boolean success;

    private String message;

    public static <T extends Response> T renderResponse(boolean success) {
        return Response.renderResponse(success, null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Response> T renderResponse(boolean success, String message) {
        Response response = new Response();
        response.setSuccess(success);
        response.setMessage(message);
        return (T) response;
    }

    public Response() {}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
