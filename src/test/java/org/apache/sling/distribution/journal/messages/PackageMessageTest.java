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

import static org.apache.sling.distribution.journal.messages.PackageMessage.abbreviate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class PackageMessageTest {

    @Test
    public void testSerialize() throws JsonGenerationException, JsonMappingException, IOException {
        byte[] pkgBinary = new String("dummy").getBytes();
        PackageMessage message = PackageMessage.builder()
            .paths(Collections.singletonList("/test"))
            .pkgBinary(pkgBinary)
            .build();
        ObjectWriter writer = new ObjectMapper().writerFor(PackageMessage.class);
        StringWriter outWriter = new StringWriter();
        writer.writeValue(outWriter, message);
        String serialized = outWriter.getBuffer().toString();
        Path path = Paths.get("src/test/resources/serialized.json");
        String expected = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining());
        assertThat(serialized, equalTo(expected));
    }

    @Test
    public void testToStringWithDefault() {
        PackageMessage message = PackageMessage.builder().build();
        assertNotNull(message.toString());
    }

    @Test
    public void testAbbreviateNullPaths() {
        assertNull(abbreviate(null));
    }

    @Test
    public void testAbbreviateEmptyPaths() {
        List<String> empty = Collections.emptyList();
        assertEquals(abbreviate(empty), empty.toString());
    }

    @Test
    public void testAbbreviateOnePaths() {
        List<String> one = Collections.singletonList("/a/path");
        assertEquals(abbreviate(one), one.toString());
    }

    @Test
    public void testAbbreviateManyPaths() {
        List<String> one = Arrays.asList("/a/path", "/another/one", "/yet/another/one");
        assertEquals(abbreviate(one), "[/a/path, ... 2 more]");
    }

}
