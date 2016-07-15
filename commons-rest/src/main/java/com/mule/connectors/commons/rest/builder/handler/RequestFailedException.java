package com.mule.connectors.commons.rest.builder.handler;

import com.google.common.base.Optional;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.fromStatusCode;

public class RequestFailedException extends RuntimeException {

    private final int status;
    private final transient Response response;

    public RequestFailedException(Response response) {
        super(String.format("[%s] - %s", response.getStatus(), Optional.<Object>fromNullable(fromStatusCode(response.getStatus())).or("Unknown error.")));
        this.status = response.getStatus();
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public Response getResponse() {
        return response;
    }
}
