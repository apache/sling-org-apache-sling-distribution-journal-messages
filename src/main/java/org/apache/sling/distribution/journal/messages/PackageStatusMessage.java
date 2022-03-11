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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageStatusMessage {

    String subSlingId;
    String subAgentName;
    String pubAgentName;
    long offset;
    PackageStatusMessage.Status status;

    public enum Status {
        /**
         * The package has been removed automatically after failing every retry attempts
         */
        REMOVED_FAILED(0),
        /**
         * The package has been removed explicitly
         */
        REMOVED(1),
        /**
         * The package has been applied
         */
        APPLIED(2);

        private int number;

        Status(int number) {
            this.number = number;
        }
        
        public int getNumber() {
            return number;
        }
        
        public static Status fromNumber(int number) {
            switch (number) {
                case 0: return REMOVED_FAILED;
                case 1: return REMOVED;
                case 2: return APPLIED;
            }
            throw new IllegalStateException("Unknown number " + number);
        }
    }
}
