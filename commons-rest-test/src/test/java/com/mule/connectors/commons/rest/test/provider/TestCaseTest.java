package com.mule.connectors.commons.rest.test.provider;

import com.mule.connectors.commons.rest.builder.request.Request;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponseAssertion;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.math.BigInteger.TEN;
import static org.easymock.EasyMock.*;

public class TestCaseTest {

    private List<Request> setupRequests;
    private List<Request> tearDownRequests;
    private Request request;
    private TestCase testCase;
    private List<RequestAndResponseAssertion> assertions;
    private Client client;
    private Response response;
    private RequestAndResponseAssertion matcher;

    @Before
    public void setup() {
        setupRequests = new ArrayList<>();
        tearDownRequests = new ArrayList<>();
        request = mock(Request.class);
        assertions = new ArrayList<>();
        testCase = new TestCase(setupRequests, request, tearDownRequests, assertions);
        client = mock(Client.class);
        response = mock(Response.class);
        matcher = mock(RequestAndResponseAssertion.class);
    }

    @Test
    public void executeTest() {
        execute();
    }

    @Test
    public void executeWithSetupTest() {
        for (int i = 0; i < TEN.intValue(); i++) {
            setupRequests.add(mock(Request.class));
        }
        execute();
    }

    @Test
    public void executeWithTearDownTest() {
        for (int i = 0; i < TEN.intValue(); i++) {
            tearDownRequests.add(mock(Request.class));
        }
        execute();
    }

    private void execute() {
        expect(request.execute(same(client))).andReturn(response);
        expect(response.getStatus()).andReturn(Response.Status.OK.getStatusCode()).anyTimes();
        for (Request setupRequest : setupRequests) {
            expect(setupRequest.execute(same(client))).andReturn(response);
            replay(setupRequest);
        }
        for (Request tearDownRequest : tearDownRequests) {
            expect(tearDownRequest.execute(same(client))).andReturn(response);
            replay(tearDownRequest);
        }
        expect(matcher.matches(anyObject(RequestAndResponse.class))).andReturn(TRUE);
        assertions.add(matcher);
        replay(request, response, client, matcher);
        testCase.execute(client);
        verify(request, response, client, matcher);
        for (Request setupRequest : setupRequests) {
            verify(setupRequest);
        }
    }
}
