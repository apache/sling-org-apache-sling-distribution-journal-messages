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
package org.apache.sling.distribution.journal;

import java.io.Closeable;
import java.net.URI;

public interface MessagingProvider {

    <T> MessageSender<T> createSender(String topic);

    default Closeable createPoller(
            String topicName,
            Reset reset,
            HandlerAdapter<?> ... adapters) {
        return createPoller(topicName, reset, null, adapters);
    }

    Closeable createPoller(String topicName, Reset reset, String assign, HandlerAdapter<?>... adapters);

    void assertTopic(String topic) throws MessagingException;

    long retrieveOffset(String topicName, Reset reset);

    String assignTo(long offset);
    
    String assignTo(Reset reset, long relativeOffset);
    
    /**
     * Return the uri of the messaging system backend.
     * The uri must be unique regarding the validity of per topic offsets.
     *  
     * @return uri
     */
    URI getServerUri();

}
