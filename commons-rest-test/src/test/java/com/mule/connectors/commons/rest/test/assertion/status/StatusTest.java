package com.mule.connectors.commons.rest.test.assertion.status;

import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.exception.StatusCodeDefinitionException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.easymock.EasyMock.*;

public class StatusTest {

    @Test
    public void validate200WithFamily() {
        Assert.assertThat(match(200, "2xx"), CoreMatchers.is(true));
    }

    @Test
    public void validate404WithEquals() {
        Assert.assertThat(match(404, "404"), CoreMatchers.is(true));
    }

    @Test
    public void validate200WithIncorrectFamily() {
        Assert.assertThat(match(200, "4xx"), CoreMatchers.is(false));
    }

    @Test
    public void validate404WithIncorrectEquals() {
        Assert.assertThat(match(404, "400"), CoreMatchers.is(false));
    }

    @Test(expected = StatusCodeDefinitionException.class)
    public void invalidFamily() {
        match(200, "2xxx");
    }

    private boolean match(int responseStatus, String expectedStatus) {
        Response resp = mock(Response.class);
        expect(resp.getStatus()).andReturn(responseStatus);
        RequestAndResponse reqAndResp = mock(RequestAndResponse.class);
        expect(reqAndResp.getResponse()).andReturn(resp);
        replay(resp, reqAndResp);
        boolean matches = new Status(expectedStatus).matches(reqAndResp);
        verify(resp, reqAndResp);
        return matches;
    }
}
