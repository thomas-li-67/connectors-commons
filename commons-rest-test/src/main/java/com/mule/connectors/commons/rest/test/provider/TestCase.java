package com.mule.connectors.commons.rest.test.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.mule.connectors.commons.rest.builder.request.Request;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponseAssertion;
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
    private final List<Matcher<? super RequestAndResponse>> responseAssertions;

    @JsonCreator
    public TestCase(@JsonProperty(value = "request", required = true) Request request,
            @JsonProperty(value = "assertions", required = true) List<RequestAndResponseAssertion> responseAssertionsParam) {
        this.request = request;
        List<Matcher<? super RequestAndResponse>> responseAssertions = new ArrayList<>();
        responseAssertions.addAll(Optional.fromNullable(responseAssertionsParam).or(new ArrayList<RequestAndResponseAssertion>()));
        this.responseAssertions = responseAssertions;
    }

    public void execute(Client client) {
        assertThat(new RequestAndResponse(request, request.execute(client)), allOf(responseAssertions));
    }
}
