package com.mule.connectors.commons.rest.builder;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.mule.connectors.commons.rest.builder.handler.ResponseHandler;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;

public class RequestBuilderTest {
    private static final String DEFAULT_PATH = "/default";
    private Invocation.Builder invocationBuilder;
    private WebTarget target;
    private Client client;
    private Response response;
    private ResponseHandler<?> responseHandler;
    private String path;
    private Map<String, Object> pathParams;
    private Map<String, Object> queryParams;
    private Map<String, Object> headers;
    private Object entity;
    private String contentType;

    @Before
    public void before() {
        response = createMock(Response.class);
        invocationBuilder = createMock(Invocation.Builder.class);
        target = createMock(WebTarget.class);
        expect(target.request()).andReturn(invocationBuilder);
        client = createMock(Client.class);
        expect(client.target(eq(""))).andReturn(target);
        responseHandler = createMock(ResponseHandler.class);

        path = DEFAULT_PATH;
        pathParams = new HashMap<>();
        queryParams = new HashMap<>();
        headers = new HashMap<>();
        contentType = MediaType.APPLICATION_XML;
    }

    @Test
    public void testSimpleGetExecute() {
        testGetExecute();
    }

    @Test
    public void testSimplePostExecute() {
        testPostExecute();
    }

    @Test
    public void testSimplePutExecute() {
        testPutExecute();
    }

    @Test
    public void testSimpleDeleteExecute() {
        testDeleteExecute();
    }

    @Test
    public void testDifferentPath() {
        path = "/root";
        testGetExecute();
    }

    @Test
    public void testDifferentPathParams() {
        pathParams = ImmutableMap.<String, Object>builder().put("test", "test").put("test2", "test2").build();
        testGetExecute();
    }

    @Test
    public void testDifferentQueryParams() {
        queryParams = ImmutableMap.<String, Object>builder().put("test", "test").put("test2", "test2").build();
        testGetExecute();
    }

    @Test
    public void testDifferentHeaders() {
        headers = ImmutableMap.<String, Object>builder().put("test", "test").put("test2", "test2").build();
        testGetExecute();
    }

    private void testGetExecute() {
        expect(invocationBuilder.get()).andReturn(response);
        testExecute(RequestBuilder.get(client, path));
    }

    private void testPostExecute() {
        expect(invocationBuilder.post(anyObject(Entity.class))).andReturn(response);
        testExecute(RequestBuilder.post(client, path));
    }

    private void testPutExecute() {
        expect(invocationBuilder.put(anyObject(Entity.class))).andReturn(response);
        testExecute(RequestBuilder.put(client, path));
    }

    private void testDeleteExecute() {
        expect(invocationBuilder.delete()).andReturn(response);
        testExecute(RequestBuilder.delete(client, path));
    }

    private void testExecute(RequestBuilder requestBuilder) {
        expect(invocationBuilder.accept(eq(MediaType.APPLICATION_XML))).andReturn(invocationBuilder);
        expect(target.path(eq(path))).andReturn(target);
        expect(target.getUri()).andReturn(URI.create(path));
        for (Map.Entry<String, Object> entry : pathParams.entrySet()) {
            requestBuilder.pathParam(entry.getKey(), entry.getValue());
        }
        expect(target.resolveTemplates(EasyMock.eq(pathParams))).andReturn(target);
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            requestBuilder.queryParam(entry.getKey(), entry.getValue());
            expect(target.queryParam(EasyMock.eq(entry.getKey()), EasyMock.eq(entry.getValue()))).andReturn(target);
        }
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
            expect(invocationBuilder.header(EasyMock.eq(entry.getKey()), EasyMock.eq(entry.getValue()))).andReturn(invocationBuilder);
        }
        requestBuilder.responseHandler(responseHandler);
        expect(responseHandler.handleResponse(eq(response), EasyMock.<Class>anyObject())).andReturn(null);
        expect(response.bufferEntity()).andReturn(Boolean.TRUE);
        replay(client, target, invocationBuilder, responseHandler, response);
        requestBuilder.execute();
    }
}