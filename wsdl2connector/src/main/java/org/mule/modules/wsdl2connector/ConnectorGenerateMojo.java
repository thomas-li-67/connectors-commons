package org.mule.modules.wsdl2connector;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.mule.modules.wsdl2connector.generator.ConnectorGenerator;

import java.io.FileNotFoundException;

/**
 */
@Mojo(name = "connector-generate")
public class ConnectorGenerateMojo extends AbstractMojo {

    @Parameter
    Configuration configuration;

    public void execute() throws MojoExecutionException {

        try {
            new ConnectorGenerator(this).generate(configuration);
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException(e.getMessage());
        }

    }
}
