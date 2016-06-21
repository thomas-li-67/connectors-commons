package com.mule.connectors.commons.rest.test.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.mule.connectors.commons.rest.builder.request.Request;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponseAssertion;
import com.mule.connectors.commons.rest.test.assertion.status.Status;
import org.hamcrest.Matcher;

import javax.ws.rs.client.Client;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Class representing a test case for Rest Testing Commons API.
 */
public class TestCase {

    private final Request request;
    private final List<RequestAndResponseAssertion> assertions;
    private final List<Request> setupRequestList;
    private final List<Request> tearDownRequestList;

    @JsonCreator
    public TestCase(@JsonProperty(value = "before", required = true) List<Request> setupRequestList,
            @JsonProperty(value = "request", required = true) Request request,
            @JsonProperty(value = "after", required = true) List<Request> tearDownRequestList,
            @JsonProperty(value = "assertions", required = true) List<RequestAndResponseAssertion> assertions) {
        this.setupRequestList = Optional.fromNullable(tearDownRequestList).or(new ArrayList<Request>());
        this.request = request;
        this.tearDownRequestList = Optional.fromNullable(setupRequestList).or(new ArrayList<Request>());
        this.assertions = Optional.fromNullable(assertions).or(new ArrayList<RequestAndResponseAssertion>());
    }

    public void execute(Client client) {
        Matcher<RequestAndResponse> statusMatcher = new Status("2xx");
        for (Request setupRequest : setupRequestList) {
            assertThat(new RequestAndResponse(setupRequest, setupRequest.execute(client)), statusMatcher);
        }
        List<Matcher<? super RequestAndResponse>> matchers = new ArrayList<>();
        matchers.addAll(this.assertions);
        assertThat(new RequestAndResponse(request, request.execute(client)), allOf(matchers));
        for (Request tearDownRequest : tearDownRequestList) {
            assertThat(new RequestAndResponse(tearDownRequest, tearDownRequest.execute(client)), statusMatcher);
        }
    }
}
