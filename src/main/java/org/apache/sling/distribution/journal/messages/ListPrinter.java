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

import java.util.Iterator;
import java.util.List;

public final class ListPrinter {
    
    private ListPrinter() {
    }

    static String print(List<String> list, boolean abbreviate) {
        if (list == null) {
            return null;
        }
        if (!abbreviate) {
            return list.toString();
        }
        Iterator<String> iter = list.iterator();
        StringBuilder abbr = new StringBuilder();
        abbr.append("[");
        if (iter.hasNext()) {
            abbr.append(iter.next());
        }
        if (iter.hasNext()) {
            abbr.append(", ... ");
            abbr.append(list.size() - 1);
            abbr.append(" more");
        }
        abbr.append("]");
        return abbr.toString();
    }

}
