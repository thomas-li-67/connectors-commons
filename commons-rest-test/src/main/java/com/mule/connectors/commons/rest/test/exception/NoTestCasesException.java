package com.mule.connectors.commons.rest.test.exception;

import java.io.File;

/**
 * Exception thrown when there are no test cases available on the test cases directory.
 */
public class NoTestCasesException extends TestCaseException {

    public NoTestCasesException(File directory) {
        super(String.format("Directory '%s' doesn't contain any valid test cases (test cases should have the '.case' extension).", directory.getAbsolutePath()));
    }
}
