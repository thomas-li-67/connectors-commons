package com.mule.connectors.commons.rest.builder.handler;

import com.mule.connectors.commons.rest.builder.request.GetRequest;
import com.mule.connectors.commons.rest.builder.request.Request;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequestTest {

    private static final String DEFAULT_KEY = "key";
    private static final String DEFAULT_VALUE = "value";
    private Request request;

    @Before
    public void setup() {
        this.request = new GetRequest();
    }

    @Test
    public void testAddQueryParam() {
        Map<String, String> mock = mock(Map.class);
        request.setQueryParams(mock);
        expect(mock.put(same(DEFAULT_KEY), eq(DEFAULT_VALUE))).andReturn(null);
        replay(mock);
        this.request.addQueryParam(DEFAULT_KEY, DEFAULT_VALUE);
        verify(mock);
    }

    @Test
    public void testAddHeader() {
        Map<String, String> mock = mock(Map.class);
        request.setHeaders(mock);
        expect(mock.put(same(DEFAULT_KEY), eq(DEFAULT_VALUE))).andReturn(null);
        replay(mock);
        this.request.addHeader(DEFAULT_KEY, DEFAULT_VALUE);
        verify(mock);
    }

    @Test
    public void testAddPathParam() {
        Map<String, Object> mock = mock(Map.class);
        request.setPathParams(mock);
        expect(mock.put(same(DEFAULT_KEY), eq(DEFAULT_VALUE))).andReturn(null);
        replay(mock);
        this.request.addPathParam(DEFAULT_KEY, DEFAULT_VALUE);
        verify(mock);
    }

    /**
     * This test is made so that all the getter methods on Request are used. These methods are for external use and as such this is the only way to test them.
     */
    @Test
    public void testGettersAndSetters() {
        String accept = "Accept";
        request.setAccept(accept);
        assertThat(request.getAccept(), is(accept));
        String path = "path";
        request.setPath(path);
        assertThat(request.getPath(), is(path));
        Map<String, Object> pathParams = new HashMap<>();
        request.setPathParams(pathParams);
        assertThat(request.getPathParams(), is(pathParams));
        Object entity = "entity";
        request.setEntity(entity);
        assertThat(request.getEntity(), is(entity));
        Map<String, String> headers = new HashMap<>();
        request.setHeaders(headers);
        assertThat(request.getHeaders(), is(headers));
        String contentType = "contentType";
        request.setContentType(contentType);
        assertThat(request.getContentType(), is(contentType));
        Map<String, String> queryParams = new HashMap<>();
        request.setQueryParams(queryParams);
        assertThat(request.getQueryParams(), is(queryParams));
    }
}