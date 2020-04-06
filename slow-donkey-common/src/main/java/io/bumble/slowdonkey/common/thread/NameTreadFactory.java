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

package io.bumble.slowdonkey.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Named thread factory. The thread generated by the factory will have a name prefixed.
 *
 * @author shenxiangyu on 2020/04/06
 */
public class NameTreadFactory implements ThreadFactory {

    /**
     * The prefix name for each thread generated.
     */
    private String name;

    /**
     * The accumulated thread index generated by the factory.
     */
    private AtomicInteger threadIndex = new AtomicInteger(0);

    @SuppressWarnings("WeakerAccess")
    public NameTreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + "-thread-" + threadIndex.incrementAndGet());
    }
}
