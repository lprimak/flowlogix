/*
 * Copyright (C) 2011-2024 Flow Logix, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flowlogix.util;

import org.junit.jupiter.api.Test;
import java.net.URI;
import static com.flowlogix.util.ShrinkWrapManipulator.DEFAULT_SSL_PORT;
import static com.flowlogix.util.ShrinkWrapManipulator.DEFAULT_SSL_PROPERTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ShrinkWrapManipulatorTest {
    @Test
    void httpsUrl() {
        String port = System.getProperty(DEFAULT_SSL_PROPERTY, String.valueOf(DEFAULT_SSL_PORT));
        var httpsUri = ShrinkWrapManipulator.toHttpsURI(URI.create("http://localhost:1234"));
        assertEquals(URI.create(String.format("https://localhost:%s", port)), httpsUri);
    }

    @Test
    void alreadyHttpsUrl() {
        var uri = URI.create("https://localhost:1234");
        var httpsUri = ShrinkWrapManipulator.toHttpsURI(uri);
        assertSame(uri, httpsUri);
    }

    @Test
    void withoutPort() {
        var httpsUri = ShrinkWrapManipulator.toHttpsURI(URI.create("http://localhost"));
        assertEquals(URI.create(String.format("https://localhost:%s", DEFAULT_SSL_PORT)), httpsUri);
    }

    @Test
    void alreadyHttpsWithoutPort() {
        var uri = URI.create("https://localhost");
        var httpsUri = ShrinkWrapManipulator.toHttpsURI(uri);
        assertSame(uri, httpsUri);
    }
}
