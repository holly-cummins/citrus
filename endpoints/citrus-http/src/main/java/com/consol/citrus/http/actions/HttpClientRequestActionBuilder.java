/*
 * Copyright 2006-2015 the original author or authors.
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

package com.consol.citrus.http.actions;

import javax.servlet.http.Cookie;

import com.consol.citrus.actions.SendMessageAction;
import com.consol.citrus.http.message.HttpMessage;
import com.consol.citrus.http.message.HttpMessageBuilder;
import com.consol.citrus.http.message.HttpMessageUtils;
import com.consol.citrus.message.Message;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

/**
 * @author Christoph Deppisch
 * @since 2.4
 */
public class HttpClientRequestActionBuilder extends SendMessageAction.SendMessageActionBuilder<SendMessageAction, HttpClientRequestActionBuilder> {

    /** Http message to send or receive */
    private HttpMessage httpMessage = new HttpMessage();

    /**
     * Default constructor initializes http message.
     */
    public HttpClientRequestActionBuilder() {
        message(new HttpMessageBuilder(httpMessage));
    }

    @Override
    public HttpClientRequestActionBuilder payload(String payload) {
        httpMessage.setPayload(payload);
        return this;
    }

    /**
     * Adds message payload multi value map data to this builder. This is used when using multipart file upload via
     * Spring RestTemplate.
     * @param payload
     * @return
     */
    public HttpClientRequestActionBuilder payload(MultiValueMap<String,Object> payload) {
        httpMessage.setPayload(payload);
        return this;
    }

    @Override
    public HttpClientRequestActionBuilder messageName(String name) {
        httpMessage.setName(name);
        return super.messageName(name);
    }

    /**
     * Sets the request path.
     * @param path
     * @return
     */
    public HttpClientRequestActionBuilder path(String path) {
        httpMessage.path(path);
        return this;
    }

    /**
     * Sets the request method.
     * @param method
     * @return
     */
    public HttpClientRequestActionBuilder method(HttpMethod method) {
        httpMessage.method(method);
        return this;
    }

    /**
     * Set the endpoint URI for the request. This works only if the HTTP endpoint used
     * doesn't provide an own endpoint URI resolver.
     *
     * @param uri absolute URI to use for the endpoint
     * @return self
     */
    public HttpClientRequestActionBuilder uri(String uri) {
        httpMessage.uri(uri);
        return this;
    }

    /**
     * Adds a query param to the request uri.
     * @param name
     * @return
     */
    public HttpClientRequestActionBuilder queryParam(String name) {
        httpMessage.queryParam(name, null);
        return this;
    }

    /**
     * Adds a query param to the request uri.
     * @param name
     * @param value
     * @return
     */
    public HttpClientRequestActionBuilder queryParam(String name, String value) {
        httpMessage.queryParam(name, value);
        return this;
    }

    /**
     * Sets the http version.
     * @param version
     * @return
     */
    public HttpClientRequestActionBuilder version(String version) {
        httpMessage.version(version);
        return this;
    }

    /**
     * Sets the request content type header.
     * @param contentType
     * @return
     */
    public HttpClientRequestActionBuilder contentType(String contentType) {
        httpMessage.contentType(contentType);
        return this;
    }

    /**
     * Sets the request accept header.
     * @param accept
     * @return
     */
    public HttpClientRequestActionBuilder accept(String accept) {
        httpMessage.accept(accept);
        return this;
    }

    /**
     * Adds cookie to response by "Cookie" header.
     * @param cookie
     * @return
     */
    public HttpClientRequestActionBuilder cookie(Cookie cookie) {
        httpMessage.cookie(cookie);
        return this;
    }

    @Override
    public HttpClientRequestActionBuilder message(Message message) {
        HttpMessageUtils.copy(message, httpMessage);
        return this;
    }

    @Override
    public SendMessageAction doBuild() {
        return new SendMessageAction(this);
    }
}
