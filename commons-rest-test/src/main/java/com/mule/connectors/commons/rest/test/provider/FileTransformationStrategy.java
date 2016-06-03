package com.mule.connectors.commons.rest.test.provider;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.mule.connectors.commons.rest.test.TestCase;
import com.mule.connectors.commons.rest.test.exception.InvalidTestCaseFormatException;
import com.mule.connectors.commons.rest.test.exception.UnexpectedParsingException;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

/**
 * {@link Function} that transforms test case files to {@link java.util.Map.Entry Entries} that can be used to test.
 */
public class FileTransformationStrategy implements Function<File, Map.Entry<String, TestCase>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map.Entry<String, TestCase> apply(File file) {
        try {
            return new AbstractMap.SimpleEntry<>(file.getAbsolutePath(), objectMapper.readValue(file, TestCase.class));
        } catch (JsonParseException | JsonMappingException e) {
            throw new InvalidTestCaseFormatException(file, e);
        } catch (IOException e) {
            throw new UnexpectedParsingException(file, e);
        }
    }
}
