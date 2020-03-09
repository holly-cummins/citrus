/*
 * Copyright 2006-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.http.integration;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.ZipMessage;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

/**
 * @author Christoph Deppisch
 */
@Test
public class HttpServerZipFileJavaIT extends TestNGCitrusSupport {

    public static final String APPLICATION_ZIP_VALUE = "application/zip";

    @CitrusTest
    public void httpServerZipFile() {
        ZipMessage zipMessage = new ZipMessage().addEntry(new ClassPathResource("schemas"));

        given(http().client("echoHttpClient")
                .send()
                .post()
                .fork(true)
                .messageType(MessageType.BINARY)
                .message(zipMessage)
                .contentType(APPLICATION_ZIP_VALUE)
                .accept(APPLICATION_ZIP_VALUE));

        when(http().server("echoHttpServer")
                    .receive()
                    .post("/echo")
                    .messageType(MessageType.BINARY)
                    .message(zipMessage)
                    .contentType(APPLICATION_ZIP_VALUE)
                    .accept(APPLICATION_ZIP_VALUE));

        then(http().server("echoHttpServer")
                .send()
                .response(HttpStatus.OK)
                .messageType(MessageType.BINARY)
                .message(zipMessage)
                .contentType(APPLICATION_ZIP_VALUE));

        then(http().client("echoHttpClient")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.BINARY)
                .message(zipMessage)
                .contentType(APPLICATION_ZIP_VALUE));
    }
}
