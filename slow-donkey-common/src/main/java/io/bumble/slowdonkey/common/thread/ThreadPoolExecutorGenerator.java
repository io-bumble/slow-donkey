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

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Simplify the thread pool executor generation.
 *
 * @author shenxiangyu on 2020/04/06
 */
public class ThreadPoolExecutorGenerator {

    /**
     * Generate a thread pool executor.
     * The maximum number of thread will be equal to the core size for simplification.
     * The thread keep alive time is default to 60 seconds.
     * A named thread factory is used for generating thread with specific name prefixed.
     *
     * @param name the thread pool name
     * @param threadPoolSize thread pool size
     * @param threadPoolQueueCapacity thread pool queue capacity
     * @return thread pool executor
     */
    public static ThreadPoolExecutor generator(String name, int threadPoolSize, int threadPoolQueueCapacity) {
        return new ThreadPoolExecutor(threadPoolSize, threadPoolSize,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(threadPoolQueueCapacity),
                new NameTreadFactory(name));
    }
}
