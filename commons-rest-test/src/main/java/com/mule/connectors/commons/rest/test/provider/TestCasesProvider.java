package com.mule.connectors.commons.rest.test.provider;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.mule.connectors.commons.rest.test.TestCase;
import com.mule.connectors.commons.rest.test.config.TestCasesConfig;
import com.mule.connectors.commons.rest.test.exception.NoTestCasesException;
import com.mule.connectors.commons.rest.test.exception.TestCasesDirectoryNotFoundException;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class in charge of loading the {@link TestCase}s from the directory defined on the {@link TestCasesConfig}.
 */
public class TestCasesProvider {

    private final TestCasesConfig config;
    private final FileTransformationStrategy transformationStrategy = new FileTransformationStrategy();

    public TestCasesProvider(TestCasesConfig config) {
        this.config = config;
    }

    public Map<String, TestCase> getCases() {
        File testCasesDirectory = config.getTestCasesDirectory();
        if (testCasesDirectory.exists() && testCasesDirectory.isDirectory()) {
            return getCases(testCasesDirectory);
        } else {
            throw new TestCasesDirectoryNotFoundException(testCasesDirectory.getAbsolutePath());
        }
    }

    private Map<String, TestCase> getCases(File directory) {
        Map<String, TestCase> cases = new HashMap<>();
        List<File> testCaseFiles = getCaseFiles(directory);
        if (testCaseFiles.isEmpty()) {
            throw new NoTestCasesException(directory);
        }
        for (Map.Entry<String, TestCase> entries : Lists.transform(testCaseFiles, transformationStrategy)) {
            cases.put(entries.getKey(), entries.getValue());
        }
        return cases;
    }

    private List<File> getCaseFiles(File directory) {
        List<File> files = new ArrayList<>();
        for (File subDirectory : filter(directory, new DirectoryFilter())) {
            files.addAll(getCaseFiles(subDirectory));
        }
        files.addAll(Arrays.asList(filter(directory, new TestCaseFileFilter())));
        return files;
    }

    private File[] filter(File directory, FileFilter filter) {
        return Optional.fromNullable(directory.listFiles(filter)).or(new File[] {});
    }
}
