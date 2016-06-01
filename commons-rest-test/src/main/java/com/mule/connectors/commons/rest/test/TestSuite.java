package com.mule.connectors.commons.rest.test;

import com.mule.connectors.commons.rest.test.config.TestCasesConfig;
import com.mule.connectors.commons.rest.test.provider.TestCasesProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Runnable} implementation and entry point to the API.<br>
 * This suite looks up all the test cases (files with the .case extension)
 * from the cases directory (defined on cases-config.properties), parses them and runs them, displaying the results.
 */
public class TestSuite implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(TestSuite.class);

    @Override
    public void run() {

        // Load configuration.
        TestCasesConfig config = new TestCasesConfig("/cases-config.properties");

        // Read configuration from file.
        Map<String, TestCase> cases = new TestCasesProvider(config).getCases();

        // Execute request.
        Map<String, TestCaseResult> results = new HashMap<>();
        Client client = new ClientConfig(java.util.logging.Logger.getLogger(getClass().getSimpleName())).getClient();

        for (Map.Entry<String, TestCase> entry : cases.entrySet()) {
            results.put(entry.getKey(), entry.getValue().execute(client, config));
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
