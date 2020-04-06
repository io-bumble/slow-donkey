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

package io.bumble.slowdonkey.server.quorum;

/**
 * Verify if the current count matches the majority quorum.
 *
 * @author shenxiangyu on 2020/04/05
 */
public class QuorumVerifier {

    /**
     * Verify if the current number of count is more than half of the total number of count.
     *
     * @param totalCount total number of count, and total number must be singular number as 3, 5, 7...
     * @param currentCount current number of count
     * @return if matches the quorum
     */
    public static boolean verify(int totalCount, int currentCount) {
        return currentCount > (totalCount / 2);
    }
}
