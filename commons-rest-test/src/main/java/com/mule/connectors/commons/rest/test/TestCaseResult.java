package com.mule.connectors.commons.rest.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representation of the result of a run {@link TestCase}. Success is defined by the apparition of error messages.
 */
public class TestCaseResult {

    private List<String> errorMessages = new ArrayList<>();

    public void addError(String message) {
        errorMessages.add(message);
    }

    public boolean isSuccessful() {
        return errorMessages.isEmpty();
    }

    public List<String> getErrorMessages() {
        return Collections.unmodifiableList(errorMessages);
    }
}
