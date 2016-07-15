package com.mule.connectors.commons.rest.builder.handler;

import org.easymock.EasyMock;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class DefaultResponseHandlerTest {

    private Response response;
    private Object expectedParsedResponse;
    private Response.StatusType statusType;

    @Before
    public void before() {
        response = createMock(Response.class);
        statusType = EasyMock.createMock(Response.StatusType.class);
        expect(response.getStatusInfo()).andReturn(statusType).anyTimes();
    }

    @Test
    public void testSimpleHandleResponse() throws IllegalAccessException, InstantiationException {
        expect(response.getStatus()).andReturn(OK.getStatusCode());
        expect(response.readEntity(eq(String.class))).andReturn("");
        expect(statusType.getFamily()).andReturn(SUCCESSFUL);

        expectedParsedResponse = new Date();
        expect(response.readEntity(anyObject(GenericType.class))).andReturn(expectedParsedResponse);
        replay(response, statusType);

        assertThat(new DefaultResponseHandler().handleResponse(response, expectedParsedResponse.getClass()), instanceOf(expectedParsedResponse.getClass()));
        verify(response, statusType);
    }

    @Test
    public void testTypedHandleResponse() throws IllegalAccessException, InstantiationException {
        expect(response.getStatus()).andReturn(OK.getStatusCode());
        expect(response.readEntity(eq(String.class))).andReturn("");
        expect(statusType.getFamily()).andReturn(SUCCESSFUL);

        expectedParsedResponse = new HashMap<String, Object>();
        expect(response.readEntity(anyObject(GenericType.class))).andReturn(expectedParsedResponse);
        replay(response, statusType);

        assertThat(new DefaultResponseHandler().handleResponse(response, new ParameterizedTypeImpl(Map.class, String.class, Object.class)), instanceOf(Map.class));
        verify(response, statusType);
    }

    @Test
    public void testVoidHandleResponse() throws IllegalAccessException, InstantiationException {
        expect(response.getStatus()).andReturn(OK.getStatusCode());
        expect(response.readEntity(eq(String.class))).andReturn("");
        expect(statusType.getFamily()).andReturn(SUCCESSFUL);
        replay(response, statusType);

        Assert.assertNull(new DefaultResponseHandler().handleResponse(response, null));
        verify(response, statusType);
    }

    @Test
    public void testEmptyResponseHandleResponse() throws IllegalAccessException, InstantiationException {
        expect(response.getStatus()).andReturn(Response.Status.NO_CONTENT.getStatusCode());
        expect(response.readEntity(eq(String.class))).andReturn("");
        expect(statusType.getFamily()).andReturn(SUCCESSFUL);
        replay(response, statusType);

        Assert.assertNull(new DefaultResponseHandler().handleResponse(response, null));
        verify(response, statusType);
    }

    @Test
    public void testForbiddenExceptionHandleResponse() {
        testExceptionHandleResponse(FORBIDDEN.getStatusCode());
    }

    @Test
    public void testBadRequestExceptionHandleResponse() {
        testExceptionHandleResponse(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testNotAuthorizedExceptionHandleResponse() {
        testExceptionHandleResponse(UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void testNotFoundExceptionHandleResponse() {
        testExceptionHandleResponse(NOT_FOUND.getStatusCode());
    }

    @Test
    public void testNotAllowedExceptionHandleResponse() {
        testExceptionHandleResponse(METHOD_NOT_ALLOWED.getStatusCode());
    }

    @Test
    public void testNotAcceptableExceptionHandleResponse() {
        testExceptionHandleResponse(NOT_ACCEPTABLE.getStatusCode());
    }

    @Test
    public void testNotSupportedExceptionHandleResponse() {
        testExceptionHandleResponse(UNSUPPORTED_MEDIA_TYPE.getStatusCode());
    }

    @Test
    public void testInternalServerErrorExceptionHandleResponse() {
        testExceptionHandleResponse(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    public void testServiceUnavailableExceptionHandleResponse() {
        testExceptionHandleResponse(SERVICE_UNAVAILABLE.getStatusCode());
    }

    @Test
    public void testRedirectionExceptionHandleResponse() {
        testExceptionHandleResponse(TEMPORARY_REDIRECT.getStatusCode());
    }

    @Test
    public void testUnmappedClientErrorExceptionHandleResponse() {
        testExceptionHandleResponse(EXPECTATION_FAILED.getStatusCode());
    }

    @Test
    public void testUnmappedServerErrorExceptionHandleResponse() {
        testExceptionHandleResponse(BAD_GATEWAY.getStatusCode());
    }

    @Test
    public void testUnmappedExceptionHandleResponse() {
        testExceptionHandleResponse(105);
    }

    public void testExceptionHandleResponse(int responseStatusCode) {
        Response.Status mappedStatus = Response.Status.fromStatusCode(responseStatusCode);
        Response.Status.Family family = mappedStatus == null ? Response.Status.Family.INFORMATIONAL : mappedStatus.getFamily();
        expect(response.getStatus()).andReturn(responseStatusCode).anyTimes();
        expect(statusType.getFamily()).andReturn(family).anyTimes();
        expect(statusType.getStatusCode()).andReturn(responseStatusCode).anyTimes();
        expect(statusType.getReasonPhrase()).andReturn("").anyTimes();
        expect(response.readEntity(eq(String.class))).andReturn("");
        replay(response, statusType);

        try {
            new DefaultResponseHandler().handleResponse(response, null);
            fail("Expected an exception here.");
        } catch(RequestFailedException e) {
            assertThat(e.getResponse(), is(response));
            assertThat(e.getStatus(), is(responseStatusCode));
        }
    }
}
