/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.distribution.journal.messages;

import java.util.HashMap;
import java.util.Map;

public class Types {
    private static Map<Class<?>, Integer> types = new HashMap<>(); 
    static {
        types.put(Messages.DiscoveryMessage.class, 1);
        types.put(Messages.PackageMessage.class, 2);
        types.put(Messages.PackageStatusMessage.class, 3);
        types.put(Messages.CommandMessage.class, 4);
        types.put(Messages.PingMessage.class, 5);
    }
    
    public static Class<?> getType(int type, int version) {
        for (Class<?> clazz : types.keySet()) {
            Integer cType = types.get(clazz);
            if (cType == type && version == 1) {
                return clazz;
            }
        }
        throw new IllegalArgumentException(String.format("Unknown type %d and version %d", type, version));
    }
    
    public static Integer getType(Class<?> clazz) {
        return types.get(clazz);
    }
    
    public static Integer getVersion(Class<?> clazz) {
        return 1;
    }
}
