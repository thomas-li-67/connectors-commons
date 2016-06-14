package com.mule.connectors.commons.rest.test.assertion.raml;

import com.google.common.collect.Lists;
import com.mule.connectors.commons.rest.builder.request.GetRequest;
import com.mule.connectors.commons.rest.builder.request.Request;
import guru.nidi.ramltester.model.Values;
import org.easymock.EasyMock;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Form;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RamlRequestAdapterTest {

    private RamlRequestAdapter ramlRequestAdapter;
    private Request request;

    @Before
    public void setup() {
        this.request = EasyMock.mock(Request.class);
        this.ramlRequestAdapter = new RamlRequestAdapter(request);
    }

    @Test
    public void getRequestUrlTest() {
        String path = "this is a path ${variable} ${empty}";
        expect(request.getPath()).andReturn(path);
        Map<String, Object> pathParams = new TreeMap<>();
        pathParams.put("variable", "variable");
        pathParams.put("empty", null);
        expect(request.getPathParams()).andReturn(pathParams);
        replay(request);
        assertThat(ramlRequestAdapter.getRequestUrl(path, false), equalTo("this is a path variable "));
        verify(request);
    }

    @Test
    public void getMethodTest() {
        request = new GetRequest();
        ramlRequestAdapter = new RamlRequestAdapter(request);
        assertThat(ramlRequestAdapter.getMethod(), equalTo("GET"));
    }

    @Test
    public void getNullMethodTest() {
        assertThat(ramlRequestAdapter.getMethod(), nullValue());
    }

    @Test
    public void getContentTypeTest() {
        String contentType = "TYPE";
        expect(request.getContentType()).andReturn(contentType);
        replay(request);
        assertThat(ramlRequestAdapter.getContentType(), equalTo(contentType));
        verify(request);
    }

    @Test
    public void getFormValuesTest() {
        Form form = new Form();
        form.param("matchTest", "matchTest");
        expect(request.getEntity()).andReturn(form);
        replay(request);
        Values values = ramlRequestAdapter.getFormValues();
        assertThat(values.get("matchTest"), CoreMatchers.<List<Object>>equalTo(Lists.<Object>newArrayList("matchTest")));
        verify(request);
    }

    @Test
    public void getFormValuesNoValuesTest() {
        expect(request.getEntity()).andReturn("");
        replay(request);
        Values values = ramlRequestAdapter.getFormValues();
        assertThat(values.asMap().size(), is(0));
        verify(request);
    }

    @Test
    public void addHeaderTest() {
        Request request = new GetRequest();
        request.addHeader("key", "value");
        ramlRequestAdapter = new RamlRequestAdapter(request);
        Map<String, List<Object>> headers = ramlRequestAdapter.getHeaderValues().asMap();
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            assertThat(entry.getKey(), is("key"));
            assertThat(entry.getValue().get(0).toString(), is("value"));
            assertThat(entry.getValue().size(), is(1));
        }
        assertThat(headers.size(), is(1));
    }
}
