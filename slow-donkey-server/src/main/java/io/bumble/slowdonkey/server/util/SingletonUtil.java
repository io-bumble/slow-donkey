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

package io.bumble.slowdonkey.server.util;

import io.bumble.slowdonkey.common.exception.ShouldNotHappenException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Util for getting singleton instance of specific class, all the given class and its instance will be stored in the
 * cache. And it is thread safe, the instance will be unique in JVM even if it is working in multi-thread scenario.
 *
 * @author shenxiangyu on 2020/03/30
 */
public class SingletonUtil {

    /**
     * Singleton instances cache, all the singleton instances will be cached with the class of the instance as its key.
     */
    private static Map<Class, Object> instances = new ConcurrentHashMap<>();

    /**
     * Get the singleton instance for the giving class
     *
     * @param clazz class of the instance
     * @param <T> generic restrict
     * @return the singleton instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {

        T instance = (T) instances.get(clazz);
        if (instance != null) {
            return instance;
        }

        synchronized (SingletonUtil.class) {
            instance = (T) instances.get(clazz);

            if (instance == null) {
                try {
                    instance = clazz.newInstance();
                    instances.put(clazz, instance);
                    return instance;
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new ShouldNotHappenException(e.getMessage(), e);
                }
            } else {
                return instance;
            }
        }
    }
}
