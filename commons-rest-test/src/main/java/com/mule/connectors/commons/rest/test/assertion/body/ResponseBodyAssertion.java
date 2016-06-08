package com.mule.connectors.commons.rest.test.assertion.body;

import javax.ws.rs.core.Response;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponseAssertion;

/**
 * {@link Matcher} that applies a determined String Matcher to the body of a {@link Response}.
 */
public class ResponseBodyAssertion extends BaseMatcher<RequestAndResponse> implements RequestAndResponseAssertion {
    protected Matcher<String> matcher;

    public ResponseBodyAssertion(Matcher<String> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object item) {
        return matcher.matches(getResponse(item).readEntity(String.class));
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        super.describeMismatch(getResponse(item), description);
    }

    protected Response getResponse(Object item) {
        return RequestAndResponse.class.cast(item).getResponse();
    }
}
