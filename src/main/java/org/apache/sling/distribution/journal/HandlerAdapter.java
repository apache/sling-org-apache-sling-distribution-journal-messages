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

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public class HandlerAdapter<T extends Message> {
    private final MessageHandler<T> handler;
    private final Class<T> type;
    
    public HandlerAdapter(Class<T> type, MessageHandler<T> handler) {
        this.type = type;
        this.handler = handler;
    }

    public static <T extends Message> HandlerAdapter<T> create(Class<T> type, MessageHandler<T> handler) {
        return new HandlerAdapter<>(type, handler);
    }
    
    public void handle(MessageInfo info, Any any) throws InvalidProtocolBufferException { 
        T message = any.unpack(type);
        this.handler.handle(info, message);
    }
    
    public Class<? extends Message> getType() {
        return type;
    }

    public MessageHandler<T> getHandler() {
        return this.handler;
    }
}
