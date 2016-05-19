package com.mule.connectors.commons.rest.test.assertion.body;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hamcrest.core.IsEqual;

/**
 * {@link BodyAssertion} that verifies that the body is equal to the expected value.
 */
public class Equals extends BodyAssertion {

    @JsonCreator
    public Equals(@JsonProperty(value = "expected", required = true) String expectedValue) {
        super(new IsEqual(expectedValue));
    }
}
