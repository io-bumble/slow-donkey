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

package io.bumble.slowdonkey;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shenxiangyu on 2020/04/06
 */
public class ConditionAwaitTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("begin");
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {

                Lock lock = new ReentrantLock();
                Condition condition = lock.newCondition();

                lock.lock();
                System.out.println("before await");
                try {
                    condition.await(3, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("after await");

                lock.unlock();
            }
        });

        t.start();

        Thread.sleep(10000);
    }
}
