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

package io.bumble.slowdonkey.server.property;

import io.bumble.slowdonkey.common.property.AbstractSlowDonkeyPropertyHolder;
import io.bumble.slowdonkey.common.property.SlowDonkeyProperty;
import io.bumble.slowdonkey.common.util.SingletonUtil;

/**
 * @author shenxiangyu on 2020/04/05
 */
public class SlowDonkeyServerPropertyHolder extends AbstractSlowDonkeyPropertyHolder {

    private SlowDonkeyProperty dummyProp = SlowDonkeyProperty.builder().key("").value("").build();

    public SlowDonkeyServerPropertyHolder() {
        super();
    }

    public static SlowDonkeyServerPropertyHolder getInstance() {
        return SingletonUtil.getInstance(SlowDonkeyServerPropertyHolder.class);
    }

    public void loadPropertiesFromFileAndRefresh() {

        // Load the property file and read each line to the property memory
        int lineCount = 10;
        for (int i = 0; i < lineCount; i++) {

            super.propertyMap.get("key").setValue("");
        }
    }
}
