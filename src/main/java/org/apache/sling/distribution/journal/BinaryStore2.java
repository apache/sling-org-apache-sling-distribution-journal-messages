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

import org.osgi.annotation.versioning.ProviderType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@ProviderType
public interface BinaryStore2 extends BinaryStore {

    /**
     * Verifies that the given reference belongs to this BinaryStore.
     *
     * @param reference
     * @return
     */
    boolean verify(String reference);

    /**
     * Return the reference for the binary in the binary store.
     *
     * @param id          binary identifier
     * @param stream      stream to store
     * @param length      length of the stream
     * @param contentType the content type of the stream
     * @param metadata    a map of metadata to assign to the blog
     * @return
     * @throws IOException
     */
    String put(String id, InputStream stream, long length, String contentType, Map<String, String> metadata) throws IOException;

}
