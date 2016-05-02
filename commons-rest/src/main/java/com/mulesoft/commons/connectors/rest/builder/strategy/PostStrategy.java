package com.mulesoft.commons.connectors.rest.builder.strategy;

import com.mulesoft.commons.connectors.rest.builder.strategy.RequestMethodStrategy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

/**
 * {@link RequestMethodStrategy} that used the POST method.
 * 
 * @author gaston.ortiz@mulesoft.com
 */
public final class PostStrategy implements RequestMethodStrategy {

    @Override
    public Response execute(Builder requestBuilder, Object entity, String contentType) {
        return requestBuilder.post(Entity.entity(entity, contentType));
    }

    @Override
    public String toString() {
        return "POST";
    }
}
