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
import java.util.List;
import java.util.Map;

/**
 * Messaging abstraction for a journal based messaging like Apache Kafka.
 * Messages are represented as json serialisable java classes.
 * The API assumes that each MessagingProvider is mapped to exactly one partition,
 * so positions in a topic can be represented as a single long offset.
 */
public interface MessagingProvider {

    /**
     * Create sender for a specific topic
     * @param <T> type of the message
     * @param topic topic name
     * @return sender
     */
    <T> MessageSender<T> createSender(String topic);

    default Closeable createPoller(
            String topicName,
            Reset reset,
            HandlerAdapter<?> ... adapters) {
        return createPoller(topicName, reset, null, adapters);
    }

    /**
     * Create a poller which listens to a topic and starts at a given reset or assigned offset.
     * 
     * @param topicName name of the topic
     * @param reset fallback in case no assign is given or the assigned offset not valid
     * @param assign opaque implementation dependent assign string (can be null)
     * @param adapters list of listener adapters
     * @return closeable handle of the poller
     */
    Closeable createPoller(String topicName, Reset reset, String assign, HandlerAdapter<?>... adapters);

    /**
     * Create a poller which listens to a topic and starts at a given reset or assigned offset.
     * 
     * @param topicName name of the topic
     * @param reset fallback in case no assign is given or the assigned offset not valid
     * @param assign opaque implementation dependent assign string (can be null)
     * @param properties list of properties to filter the topic
     * @param adapters list of listener adapters
     * @return closeable handle of the poller
     */
    default Closeable createPoller(String topicName, Reset reset, String assign, Map<String, String> properties,
            HandlerAdapter<?>... adapters) {
        return createPoller(topicName, reset, assign, adapters);
    }

    /**
     * Validate that a topic is suitably set up for the messaging implementation
     * 
     * @param topic topic name
     * @throws MessagingException exception in case the topic is not suitable
     */
    void assertTopic(String topic) throws MessagingException;

    /**
     * Retrieve earliest or latest offset for a topic
     * 
     * @param topicName name of the topic
     * @param reset latest or earliest
     * @return offset
     */
    long retrieveOffset(String topicName, Reset reset);

    /**
     * Create assign String to feed into poller based on a given offset.
     * The inner format of the assign string is implementation specific.
     *  
     * @param offset
     * @return assign String
     */
    String assignTo(long offset);
    
    /**
     * Get assign String to feed into createPoller based on either earliest or latest and a relative offset.
     * The inner format of the assign string is implementation specific.
     * 
     * @param reset reference point
     * @param relativeOffset relative offset
     * @return assign String
     */
    String assignTo(Reset reset, long relativeOffset);
    
    /**
     * Return the uri of the messaging system backend.
     * The uri must be unique regarding the validity of per topic offsets.
     *  
     * @return uri
     */
    URI getServerUri();

    }
