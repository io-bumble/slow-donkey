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

package io.bumble.slowdonkey.server.data;

import io.bumble.slowdonkey.common.util.SingletonUtil;
import io.bumble.slowdonkey.server.persistence.TxnLogEntry;

import java.util.List;

/**
 * @author shenxiangyu on 2020/03/31
 */
public class DataTree {

    private long lastTxnId;

    private long term;

    public static DataTree getInstance() {
        return SingletonUtil.getInstance(DataTree.class);
    }

    public boolean update(TxnLogEntry txnLogEntry) {
        return false;
    }

    /**
     * Recover the data tree from bytes array.
     *
     * @param bytes bytes array
     * @return true for success
     */
    public boolean fromBytes(byte[] bytes) {
        return false;
    }

    /**
     * Serialize the data tree to data bytes array.
     *
     * @return serialized bytes array.
     */
    public byte[] toBytes() {
        return null;
    }

    public long getLastTxnId() {
        return 0;
    }

    public String getData(String path) {
        return null;
    }

    public List<String> getChildPathList(String path) {
        return null;
    }

    public void setLastTxnId(long lastTxnId) {
        this.lastTxnId = lastTxnId;
    }

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }
}
