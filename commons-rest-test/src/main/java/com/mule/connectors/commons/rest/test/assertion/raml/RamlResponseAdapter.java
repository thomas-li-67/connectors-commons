package com.mule.connectors.commons.rest.test.assertion.raml;

import guru.nidi.ramltester.model.RamlResponse;
import guru.nidi.ramltester.model.Values;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;

public class RamlResponseAdapter implements RamlResponse {

    private final int status;
    private final Values values;
    private final String content;
    private final String contentType;

    public RamlResponseAdapter(Response response) {
        this.status = response.getStatus();
        Map<String, String[]> headers = new HashMap<>();
        for (Map.Entry<String, List<Object>> entry : response.getHeaders().entrySet()) {
            headers.put(entry.getKey(), Lists.transform(entry.getValue(), Functions.toStringFunction()).toArray(new String[0]));
        }
        this.contentType = response.getMediaType().toString();
        values = new Values(headers);
        content = response.readEntity(String.class);
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public Values getHeaderValues() {
        return values;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public byte[] getContent() {
        return content.getBytes();
    }
}
