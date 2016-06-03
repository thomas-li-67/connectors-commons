package com.mule.connectors.commons.rest.test;

import com.mule.connectors.commons.rest.test.config.TestCasesConfig;
import com.mule.connectors.commons.rest.test.provider.TestCasesProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Runnable} implementation and entry point to the API.<br>
 * This suite looks up all the test cases (files with the .case extension)
 * from the cases directory (defined on cases-config.properties), parses them and runs them, displaying the results.
 */
public class TestSuite implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TestSuite.class);

    @Override
    public void run() {

        // Load configuration.
        TestCasesConfig config = new TestCasesConfig("/cases-config.properties");

        // Read configuration from file.
        Map<String, TestCase> cases = new TestCasesProvider(config).getCases();

        // Execute request.
        Map<String, TestCaseResult> results = new HashMap<>();
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new LoggingFilter(java.util.logging.Logger.getLogger(getClass().getSimpleName()), true));
        Client client = ClientBuilder.newClient(clientConfig);

        for (Map.Entry<String, TestCase> entry : cases.entrySet()) {
            results.put(entry.getKey(), entry.getValue().execute(client));
        }

        // Present results.
        for (Map.Entry<String, TestCaseResult> entry : results.entrySet()) {
            if (entry.getValue().isSuccessful()) {
                logger.info(String.format("%s: OK", entry.getKey()));
            } else {
                logger.error(String.format("%s: FAILED", entry.getKey()));
                for (String errorMessage : entry.getValue().getErrorMessages()) {
                    logger.error(String.format("    %s", errorMessage));
                }
            }
        }
    }
}
