package com.mule.connectors.commons.rest.test.assertion.raml;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.assertion.RequestAndResponseAssertion;
import guru.nidi.ramltester.RamlDefinition;
import guru.nidi.ramltester.RamlLoaders;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ValidRaml extends BaseMatcher<RequestAndResponse> implements RequestAndResponseAssertion {

    private final RamlDefinition ramlDefinition;

    @JsonCreator
    public ValidRaml(@JsonProperty(value = "file", required = true) String ramlFilePath) {
        ramlDefinition = RamlLoaders.fromClasspath(ValidRaml.class).load(ramlFilePath);
    }

    @Override
    public boolean matches(Object item) {
        RequestAndResponse requestAndResponse = RequestAndResponse.class.cast(item);
        return ramlDefinition.testAgainst(new RamlRequestAdapter(requestAndResponse.getRequest()), new RamlResponseAdapter(requestAndResponse.getResponse())).isEmpty();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("To be empty");
    }
}
