package com.mule.connectors.commons.rest.test.assertion.raml;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import guru.nidi.ramltester.model.RamlResponse;
import guru.nidi.ramltester.model.Values;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RamlResponseAdapter implements RamlResponse {

    private final int status;
    private final Values values;
    private final byte[] content;
    private final String contentType;

    public RamlResponseAdapter(Response response) {
        this.status = response.getStatus();
        Map<String, String[]> headers = new HashMap<>();
        for (Map.Entry<String, List<Object>> entry : response.getHeaders().entrySet()) {
            headers.put(entry.getKey(), Lists.transform(entry.getValue(), Functions.toStringFunction()).toArray(new String[0]));
        }
        this.contentType = response.getMediaType().getType();
        values = new Values(headers);
        content = response.readEntity(String.class).getBytes();
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
        return content;
    }
}
