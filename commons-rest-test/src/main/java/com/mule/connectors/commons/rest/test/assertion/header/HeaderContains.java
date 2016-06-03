package com.mule.connectors.commons.rest.test.assertion.header;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;
import java.util.Map;

/**
 * {@link ResponseHeaderAssertion} that verifies that a determined header is present.
 */
public class HeaderContains extends ResponseHeaderAssertion {

    private final Matcher<Iterable<? super String>> matcher;

    @JsonCreator
    public HeaderContains(@JsonProperty(value = "expected", required = true) String expectedValue) {
        this.matcher = CoreMatchers.hasItem(expectedValue);
    }

    @Override
    public boolean matches(Map<String, List<Object>> headers) {
        return matcher.matches(headers.keySet());
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }
}
