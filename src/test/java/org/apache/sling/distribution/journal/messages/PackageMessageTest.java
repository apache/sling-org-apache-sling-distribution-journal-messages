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

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.Assert;
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
        Assert.assertThat(serialized, equalTo(expected));
    }
}
