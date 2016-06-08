package com.mule.connectors.commons.rest.test.assertion.status;

import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponseAssertion;
import com.mule.connectors.commons.rest.test.exception.StatusCodeDefinitionException;

/**
 * {@link Matcher} that validates that the status code of a {@link Response} is the expected value, which can either be a status code (three digit integer) or a family (2xx or 4xx
 * for example).
 */
public class Status extends BaseMatcher<RequestAndResponse> implements RequestAndResponseAssertion {

    private Matcher<String> matcher;
    private static Pattern FAMILY_PATTERN = Pattern.compile("\\d{1}(x){2}");

    @JsonCreator
    public Status(@JsonProperty(value = "expectedValue", required = true) String expectedValue) {
        if (isNumeric(expectedValue)) {
            matcher = CoreMatchers.is(expectedValue);
        } else if (isFamily(expectedValue)) {
            matcher = CoreMatchers.startsWith(expectedValue.substring(0, 1));
        } else {
            throw new StatusCodeDefinitionException(expectedValue);
        }
    }

    private boolean isFamily(String expectedValue) {
        return FAMILY_PATTERN.matcher(expectedValue).matches();
    }

    @Override
    public boolean matches(Object item) {
        return matcher.matches(Integer.toString(getResponse(item).getStatus()));
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }

    private Response getResponse(Object item) {
        return RequestAndResponse.class.cast(item).getResponse();
    }
}
