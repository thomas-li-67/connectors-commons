package com.mule.connectors.commons.rest.test;

import com.mule.connectors.commons.rest.test.config.TestCasesConfig;
import com.mule.connectors.commons.rest.test.provider.TestCase;
import com.mule.connectors.commons.rest.test.provider.TestCasesProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.ws.rs.client.Client;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestRun implements Runnable {

    private final TestCase testCase;
    private final Client client;
    private final String path;

    public TestRun(String path, TestCase testCase, Client client) {
        this.path = path;
        this.testCase = testCase;
        this.client = client;
    }

    @Parameterized.Parameters(name = "Test case for \"{0}\"")
    public static Collection<Object[]> getCases() {
        return new TestCasesProvider(new TestCasesConfig("/cases-config.properties")).getCases();
    }

    @Test
    @Override
    public void run() {
        testCase.execute(client);
    }
}
