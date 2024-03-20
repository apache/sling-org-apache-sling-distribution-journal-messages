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

import lombok.*;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageMessage {
    String pubSlingId;
    ReqType reqType;
    String pkgId;
    String pkgType;
    long pkgLength;
    byte[] pkgBinary;
    String pkgBinaryRef;
    String pubAgentName;
    String userId;

    @Builder.Default
    List<String> paths = new ArrayList<>();

    @Builder.Default
    List<String> deepPaths = new ArrayList<>();

    @Builder.Default
    Map<String, String> metadata = new HashMap<>();

    public enum ReqType {
        ADD,
        DELETE,
        INVALIDATE,
        TEST;
    }
    
    public String toString() {
        return toString(true);
    }

    public String toString(boolean abbreviate) {
        StringBuilder out = new StringBuilder();
        out.append("PackageMessage(pubSlingId=");
        out.append(pubSlingId);
        out.append(", reqType=");
        out.append(reqType);
        out.append(", pkgId=");
        out.append(pkgId);
        out.append(", pkgType=");
        out.append(pkgType);
        out.append(", pkgLength=");
        out.append(pkgLength);
        out.append(", pubAgentName=");
        out.append(pubAgentName);
        out.append(", userId=");
        out.append(userId);
        if (pkgBinary != null) {
            out.append(", pkgBinary.length=");
            out.append(pkgBinary.length);
        }
        out.append(", paths=");
        out.append(ListPrinter.print(paths, abbreviate));
        out.append(", deepPaths=");
        out.append(ListPrinter.print(deepPaths, abbreviate));
        out.append(", metadata=");
        out.append(metadata);
        out.append(")");
        return out.toString();
    }
}
