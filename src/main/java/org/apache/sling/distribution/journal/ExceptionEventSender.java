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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

/**
 * This is used by the journal adapter to send events for exceptions that happen
 * when distribution talks to the journal.
 */
public class ExceptionEventSender {

    public static final String ERROR_TOPIC = "org/apache/sling/distribution/journal/errors";
    private final EventAdmin eventAdmin;

    public ExceptionEventSender(@Nullable EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    private Event createErrorEvent(Exception e) {
        Map<String, String> props = new HashMap<>();
        props.put("type", e.getClass().getName());
        props.put("message", e.getMessage());
        return new Event(ERROR_TOPIC, props);
    }

    public void send(Exception e) {
        if (eventAdmin != null) {
            Event event = createErrorEvent(e);
            eventAdmin.postEvent(event);
        }
    }
}
