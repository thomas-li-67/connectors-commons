package com.mule.connectors.commons.rest.test.assertion;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mule.connectors.commons.rest.test.assertion.body.Contains;
import com.mule.connectors.commons.rest.test.assertion.body.Equals;
import com.mule.connectors.commons.rest.test.assertion.header.HeaderContains;
import com.mule.connectors.commons.rest.test.assertion.header.HeaderEquals;
import com.mule.connectors.commons.rest.test.assertion.raml.ValidRaml;
import org.hamcrest.Matcher;

/**
 * Parent {@link Matcher} for all the Matchers defined on this class. The purpose of this interface is to map the mappers to json.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Contains.class, name = "contains"),
        @JsonSubTypes.Type(value = Equals.class, name = "equals"),
        @JsonSubTypes.Type(value = HeaderContains.class, name = "hasHeader"),
        @JsonSubTypes.Type(value = HeaderEquals.class, name = "headerEquals"),
        @JsonSubTypes.Type(value = ValidRaml.class, name = "raml")
})
public interface RequestAndResponseAssertion extends Matcher<RequestAndResponse> {

}
