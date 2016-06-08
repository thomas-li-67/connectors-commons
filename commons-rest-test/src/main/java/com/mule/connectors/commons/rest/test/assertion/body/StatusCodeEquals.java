package com.mule.connectors.commons.rest.test.assertion.body;

import javax.ws.rs.core.Response;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link Matcher} that validates that the status code of a {@link Response} is the expected value.
 */
public class StatusCodeEquals extends ResponseBodyAssertion {

    @JsonCreator
    public StatusCodeEquals(@JsonProperty(value = "expectedValue", required = true) String expectedValue) {
        super(CoreMatchers.is(expectedValue));
    }

    @Override
    public boolean matches(Object item) {
        return matcher.matches(Integer.toString(getResponse(item).getStatus()));
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }
}
