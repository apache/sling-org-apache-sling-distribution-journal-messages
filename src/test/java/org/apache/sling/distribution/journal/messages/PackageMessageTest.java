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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class PackageMessageTest {

    private String serializePackageMessage(PackageMessage message) throws IOException {
        ObjectWriter writer = new ObjectMapper().writerFor(PackageMessage.class);
        StringWriter outWriter = new StringWriter();
        writer.writeValue(outWriter, message);
        return outWriter.getBuffer().toString();
    }
    
    @Test
    public void testDefaults() {
        PackageMessage message = PackageMessage.builder()
            .build();
        assertThat(message.getPaths(), notNullValue());
        assertThat(message.getDeepPaths(), notNullValue());
        assertThat(message.getMetadata(), notNullValue());
        assertThat(message.toString(), notNullValue());
    }

    @Test
    public void testSerialize() throws IOException {
        byte[] pkgBinary = "dummy".getBytes();
        PackageMessage message = PackageMessage.builder()
            .paths(Collections.singletonList("/test"))
            .pkgBinary(pkgBinary)
            .metadata(Collections.singletonMap("testMetadataField", "test"))
            .build();
        String serialized = serializePackageMessage(message);
        Path path = Paths.get("src/test/resources/serialized.json");
        String expected = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining());
        assertThat(serialized, equalTo(expected));
    }

    @Test
    public void testSerializeWithoutMetadata() throws IOException {
        byte[] pkgBinary = "dummy".getBytes();
        PackageMessage message = PackageMessage.builder()
                .paths(Collections.singletonList("/test"))
                .pkgBinary(pkgBinary)
                .build();
        String serialized = serializePackageMessage(message);
        Path path = Paths.get("src/test/resources/serialized-no-metadata.json");
        String expected = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining());
        assertThat(serialized, equalTo(expected));
    }

}
