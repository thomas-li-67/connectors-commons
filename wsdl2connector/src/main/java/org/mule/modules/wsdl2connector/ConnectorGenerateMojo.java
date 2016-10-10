package org.mule.modules.wsdl2connector;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.mule.modules.wsdl2connector.generator.ConnectorGenerator;

/**
 */
@Mojo(name = "connector-generate")
public class ConnectorGenerateMojo extends AbstractMojo {

    @Parameter(property = "basePath")
    private String basePath;

    @Parameter(property = "serviceClass")
    private String serviceClass;

    @Parameter(property = "clientRetrievalMethod")
    private String clientRetrievalMethod;

    @Parameter(property = "basePackage")
    private String basePackage;

    public void execute() throws MojoExecutionException {
        new ConnectorGenerator(this).generate(basePath, serviceClass, clientRetrievalMethod, basePackage);
    }
}
