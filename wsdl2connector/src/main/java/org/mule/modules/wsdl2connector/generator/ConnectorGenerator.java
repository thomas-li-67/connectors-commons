package org.mule.modules.wsdl2connector.generator;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.mule.modules.wsdl2connector.Configuration;
import org.mule.modules.wsdl2connector.generator.io.ClassWriter;
import org.mule.modules.wsdl2connector.generator.io.ClientClassReader;
import org.mule.modules.wsdl2connector.generator.io.ConnectorClassReader;
import org.mule.modules.wsdl2connector.generator.model.config.BaseConfigClass;
import org.mule.modules.wsdl2connector.generator.model.config.ConcreteConfigClass;

import java.io.FileNotFoundException;

/**
 * Default domain class that handles the main operation of generating sources.
 */
public class ConnectorGenerator {

    private final Mojo mojo;

    public ConnectorGenerator(Mojo mojo) {
        this.mojo = mojo;
    }

    /**
     * Generates sources.
     */
    public void generate(Configuration conf) throws MojoExecutionException, FileNotFoundException {

        ClassWriter classWriter = new ClassWriter(mojo, conf.getBasePath());
        log("Retrieving client class.");
        String clientFullyQualifiedName = new ClientClassReader(mojo).read(conf.getBasePath(), conf.getServiceClass(), conf.getClientRetrievalMethod());
        log("Writing connector classes.");
        BaseConfigClass baseConfigClass = new BaseConfigClass(conf.getBasePackage(), clientFullyQualifiedName);
        classWriter.writeClass(baseConfigClass);
        log("Base config class class created.");
        generateConfiguration(conf, classWriter, baseConfigClass);
        classWriter.writeClass(new ConnectorClassReader(conf.getBasePackage()).read(baseConfigClass, conf.getBasePath(), clientFullyQualifiedName, classWriter));
        log("Connector class created.");
    }



    public void generateConfiguration(Configuration conf, ClassWriter classWriter, BaseConfigClass baseConfigClass) throws MojoExecutionException {

        switch (conf.getType()) {
            case KERBEROS:
                ConcreteConfigClass kerberosConfigClass = new ConcreteConfigClass("KerberosConfig", baseConfigClass, conf.getServiceClass(), conf.getClientRetrievalMethod());
                classWriter.writeClass("XRMSpnegoClientAction", "XRMSpnegoClientAction", kerberosConfigClass);
                log("XRMSpnegoClientAction class created.");
                classWriter.writeClass("KerberosConfigClass", kerberosConfigClass);
                log("Kerberos config class created.");
                break;

            case NTLM:
                ConcreteConfigClass ntlmConfigClass = new ConcreteConfigClass("NTLMConfig", baseConfigClass, conf.getServiceClass(), conf.getClientRetrievalMethod());
                classWriter.writeClass("NTLMConfigClass", ntlmConfigClass);
                log("NTLM config class created.");
                break;
            default:
                throw new MojoExecutionException("Invalid type.");
        }
    }

    private void log(String message) {
        mojo.getLog().info(message);
    }

}
