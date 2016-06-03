package com.mule.connectors.commons.rest.builder;

import java.io.UnsupportedEncodingException;

/**
 * Wrapper exception that indicates that an encoding issue happened on the basic Authorization process.
 */
public class BasicAuthorizationEncodingException extends RuntimeException {

    public BasicAuthorizationEncodingException(UnsupportedEncodingException e) {
        super("An encoding error occurred while setting the basic authorization.", e);
    }
}
