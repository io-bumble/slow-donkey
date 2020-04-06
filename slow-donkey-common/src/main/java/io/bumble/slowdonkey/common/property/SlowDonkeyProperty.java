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

package io.bumble.slowdonkey.common.property;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

/**
 * Define the properties for both client and server side
 *
 * @author shenxiangyu on 2020/04/05
 */
public class SlowDonkeyProperty {

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIntValue() {
        return NumberUtils.toInt(value, 0);
    }

    public boolean getBoolValue() {
        return BooleanUtils.toBoolean(value);
    }

    @Override
    public String toString() {
        return key + '=' + value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String key;

        private String value;

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public SlowDonkeyProperty build() {
            SlowDonkeyProperty property = new SlowDonkeyProperty();
            property.setKey(Optional.ofNullable(this.key).orElse(StringUtils.EMPTY));
            property.setValue(Optional.ofNullable(this.value).orElse(StringUtils.EMPTY));
            return property;
        }
    }
}
