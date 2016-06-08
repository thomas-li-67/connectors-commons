package com.mule.connectors.commons.rest.test.assertion.status;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import javax.ws.rs.core.Response;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.mule.connectors.commons.rest.test.assertion.RequestAndResponse;
import com.mule.connectors.commons.rest.test.exception.StatusCodeDefinitionException;

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

    public boolean match(int responseStatus, String family) {
        Status status = new Status(family);
        Response resp = mock(Response.class);
        expect(resp.getStatus()).andReturn(responseStatus);
        RequestAndResponse reqAndResp = mock(RequestAndResponse.class);
        expect(reqAndResp.getResponse()).andReturn(resp);
        replay(resp, reqAndResp);
        boolean matches = status.matches(reqAndResp);
        verify(resp, reqAndResp);
        return matches;
    }
}
