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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Abstraction for storing binaries
 */
@ProviderType
public interface BinaryStore {

    /**
     * Return an input stream for the identifier
     *
     * @param reference binary reference
     * @return
     * @throws IOException
     */
    InputStream get(String reference) throws IOException;

    /**
     * Return the reference for the binary in the binary store
     *
     * @param id binary identifier
     * @param stream stream to store
     * @param length length of the stream
     * @return
     * @throws IOException
     */
    String put(String id, InputStream stream, long length) throws IOException;

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
    String put(String id, InputStream stream, long length, String contentType, Map<String, String> metadata)
            throws IOException;

    /**
     * Return a reference for a binary that was stored with one of the {@code put} methods.
     * The reference can be used like the one returned by {@code put}: {@link #get(String)} reads the binary
     * and {@link #verify(String)} returns {@code true} for it. A store that hands out time-limited references,
     * for example signed URLs, returns a new one with a fresh validity. Implementations are not required to
     * check that a binary exists for the identifier.
     *
     * @param id binary identifier, as passed to {@code put}
     * @return the reference for the binary
     * @throws IOException if the reference cannot be created
     */
    String getReference(String id) throws IOException;
}
