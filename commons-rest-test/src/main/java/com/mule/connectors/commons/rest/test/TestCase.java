package com.mule.connectors.commons.rest.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mule.connectors.commons.rest.builder.request.Request;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponseAssertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Class representing a test case for Rest Testing Commons API.
 */
public class TestCase {
    private static final Logger logger = LoggerFactory.getLogger(TestCase.class);

    private final Request request;
    private final List<RequestAndResponseAssertion> responseAssertions;

    @JsonCreator
    public TestCase(@JsonProperty(value = "request", required = true) Request request,
            @JsonProperty(value = "assertions", required = true) List<RequestAndResponseAssertion> responseAssertions) {
        this.request = request;
        this.responseAssertions = Collections.unmodifiableList(responseAssertions);
    }

    public TestCaseResult execute(Client client) {
        TestCaseResult result = new TestCaseResult();
        RequestAndResponse requestAndResponse = new RequestAndResponse(request, request.execute(client));
        for (RequestAndResponseAssertion responseAssertion : responseAssertions) {
            try {
                assertThat(requestAndResponse, responseAssertion);
            } catch (AssertionError e) {
                logger.trace("Error found.", e);
                result.addError(e.getLocalizedMessage());
            }
        }
        return result;
    }
}
