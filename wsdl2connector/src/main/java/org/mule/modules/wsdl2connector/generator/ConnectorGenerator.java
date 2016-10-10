package org.mule.modules.wsdl2connector.generator;

import org.apache.maven.plugin.Mojo;
import org.mule.modules.wsdl2connector.generator.io.ClassWriter;
import org.mule.modules.wsdl2connector.generator.io.ClientClassReader;
import org.mule.modules.wsdl2connector.generator.io.ConnectorClassReader;
import org.mule.modules.wsdl2connector.generator.model.config.BaseConfigClass;
import org.mule.modules.wsdl2connector.generator.model.config.ConcreteConfigClass;

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
    public void generate(String basePath, String serviceFullyQualifiedName, String clientRetrievalMethod, String basePackage) {
        log("Retrieving client class.");
        String clientFullyQualifiedName = new ClientClassReader(mojo).read(basePath, serviceFullyQualifiedName, clientRetrievalMethod);
        log("Writing connector classes.");
        ClassWriter classWriter = new ClassWriter(mojo, basePath);
        BaseConfigClass baseConfigClass = new BaseConfigClass(basePackage, clientFullyQualifiedName);
        classWriter.writeClass(baseConfigClass);
        log("Base config class class created.");
        ConcreteConfigClass kerberosConfigClass = new ConcreteConfigClass("KerberosConfig", baseConfigClass, serviceFullyQualifiedName, clientRetrievalMethod);
        classWriter.writeClass("XRMSpnegoClientAction", "XRMSpnegoClientAction", kerberosConfigClass);
        log("XRMSpnegoClientAction class created.");
        classWriter.writeClass("KerberosConfigClass", kerberosConfigClass);
        log("Kerberos config class created.");
        ConcreteConfigClass ntlmConfigClass =  new ConcreteConfigClass("NTLMConfig", baseConfigClass, serviceFullyQualifiedName, clientRetrievalMethod);
        classWriter.writeClass("NTLMAuthenticator", "NTLMAuthenticator", kerberosConfigClass);
        log("NTLMAuthenticator class created.");
        classWriter.writeClass("NTLMConfigClass", ntlmConfigClass);
        log("NTLM config class created.");
        classWriter.writeClass(new ConnectorClassReader(basePackage).read(baseConfigClass, basePath, clientFullyQualifiedName));
        log("Connector class created.");
    }

    private void log(String message) {
        mojo.getLog().info(message);
    }


}
