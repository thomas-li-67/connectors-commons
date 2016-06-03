package com.mule.connectors.commons.rest.test.assertion.raml;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.google.common.base.Optional;
import com.mule.connectors.commons.rest.builder.request.Request;
import guru.nidi.ramltester.model.RamlRequest;
import guru.nidi.ramltester.model.Values;

import javax.ws.rs.core.Form;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RamlRequestAdapter implements RamlRequest {

    private final Request request;

    public RamlRequestAdapter(Request request) {
        this.request = request;
    }

    @Override
    public String getRequestUrl(String baseUri, boolean includeServletPath) {
        String path = request.getPath();
        for (Map.Entry<String, Object> pathParam : request.getPathParams().entrySet()) {
            path = path.replace(String.format("${%s}", pathParam), Optional.fromNullable(pathParam.getValue()).or("").toString());
        }
        return path;
    }

    @Override
    public String getMethod() {
        for (JsonSubTypes.Type type : Request.class.getAnnotation(JsonSubTypes.class).value()) {
            if (type.value().equals(request.getClass())) {
                return type.name();
            }
        }
        return null;
    }

    @Override
    public Values getQueryValues() {
        return toValues(request.getQueryParams());
    }

    @Override public Values getFormValues() {
        try {
            Values result = new Values();
            for (Map.Entry<String, List<String>> entry : Form.class.cast(request.getEntity()).asMap().entrySet()) {
                result.addValues(entry.getKey(), entry.getValue());
            }
            return result;
        } catch (ClassCastException e) {
            return new Values();
        }
    }

    @Override
    public Values getHeaderValues() {
        return toValues(request.getHeaders());
    }

    @Override
    public String getContentType() {
        return request.getContentType();
    }

    @Override
    public byte[] getContent() {
        return request.getEntity().toString().getBytes();
    }

    private Values toValues(Map<String, String> params) {
        Map<String, String[]> result = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.put(entry.getKey(), new String[] { entry.getValue() });
        }
        return new Values(result);
    }
}
