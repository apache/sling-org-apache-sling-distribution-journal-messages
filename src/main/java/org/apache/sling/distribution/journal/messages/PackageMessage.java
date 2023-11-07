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
@Builder(builderMethodName = "builderWithMetadata")
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

    Map<String, String> metadata = new HashMap<>();

    public enum ReqType {
        ADD,
        DELETE,
        INVALIDATE,
        TEST;
    }

    // Default constructor containing the minimum required fields
    @Builder(builderMethodName = "builder")
    public PackageMessage(String pubSlingId, ReqType reqType, String pkgId, String pkgType,
                          long pkgLength, byte[] pkgBinary, String pkgBinaryRef,
                          String pubAgentName, String userId, List<String> paths,
                          List<String> deepPaths) {
        this.pubSlingId = pubSlingId;
        this.reqType = reqType;
        this.pkgId = pkgId;
        this.pkgType = pkgType;
        this.pkgBinaryRef = pkgBinaryRef;
        this.pubAgentName = pubAgentName;
        this.userId = userId;
        this.pkgLength = pkgLength;
        this.pkgBinary = pkgBinary;
        this.paths = paths;
        this.deepPaths = deepPaths;
    }

    public String toString() {
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
        out.append(abbreviate(paths));
        out.append(", deepPaths=");
        out.append(abbreviate(deepPaths));
        out.append(", metadata=");
        out.append(metadata);
        out.append(")");
        return out.toString();
    }

    static String abbreviate(List<String> list) {
        if (list == null) {
            return null;
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
