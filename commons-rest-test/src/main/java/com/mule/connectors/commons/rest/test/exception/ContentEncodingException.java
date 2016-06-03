package com.mule.connectors.commons.rest.test.exception;

import java.io.UnsupportedEncodingException;

/**
 * Exception thrown when the content of a request to be validated is not UTF-8 format.
 */
public class ContentEncodingException extends TestCaseException {

    public ContentEncodingException(UnsupportedEncodingException cause) {
        super("An error occurred while parsing the content using UTF-8 charset.", cause);
    }
}
