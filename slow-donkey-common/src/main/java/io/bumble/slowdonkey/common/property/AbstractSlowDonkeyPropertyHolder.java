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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Can be reused for both client and server side
 *
 * @author shenxiangyu on 2020/04/05
 */
public class AbstractSlowDonkeyPropertyHolder {

    protected Map<String, SlowDonkeyProperty> propertyMap = new HashMap<>();

    public AbstractSlowDonkeyPropertyHolder() {
        for (Field field : this.getClass().getFields()) {
            if (field.getType() == SlowDonkeyProperty.class) {
                try {
                    SlowDonkeyProperty slowDonkeyProperty = (SlowDonkeyProperty) field.get(this);
                    propertyMap.put(slowDonkeyProperty.getKey(), slowDonkeyProperty);
                } catch (IllegalAccessException e) {
                    Logger logger = LoggerFactory.getLogger(AbstractSlowDonkeyPropertyHolder.class);
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
