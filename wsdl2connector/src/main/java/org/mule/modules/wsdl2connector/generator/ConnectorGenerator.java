package org.mule.modules.wsdl2connector.generator;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.mule.modules.wsdl2connector.Configuration;
import org.mule.modules.wsdl2connector.generator.io.ClassWriter;
import org.mule.modules.wsdl2connector.generator.io.ClientClassReader;
import org.mule.modules.wsdl2connector.generator.io.ConnectorClassReader;
import org.mule.modules.wsdl2connector.generator.model.config.BaseConfigClass;
import org.mule.modules.wsdl2connector.generator.model.config.ConcreteConfigClass;
import org.mule.modules.wsdl2connector.generator.model.connector.Parameter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.mule.modules.wsdl2connector.generator.model.entity.ModelEntityClassBuilder.writeModelEntityClass;

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
        generateModelEntities(conf.getBasePath(), conf.getBasePackage(), clientFullyQualifiedName, classWriter);
        classWriter.writeClass(new ConnectorClassReader(conf.getBasePackage()).read(baseConfigClass, conf.getBasePath(), clientFullyQualifiedName, classWriter));
        log("Connector class created.");
    }

    private void generateModelEntities(String basePath, String basePackage, String clientFullyQualifiedName, ClassWriter classWriter) throws FileNotFoundException {
        Set<Parameter> parameters = new HashSet<>();
        Scanner scanner = new Scanner(new File(format("%s/%s.java", basePath, clientFullyQualifiedName.replace(".", "/"))));
        HashMap<String, String> createdModelEntities = new HashMap<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (!line.startsWith("@") && !line.startsWith("package ") && !line.isEmpty() && !line.startsWith("/") && !line.startsWith("*") && !line.startsWith("public ") && !line.startsWith("import ")) {
                if (!(line.startsWith("}") || line.startsWith(")"))) {
                    List<String> param = Arrays.asList(line.split(" "));
                    parameters.add(new Parameter(null, param.get(0).replace(",", "")));
                }
            }
        }
        parameters = parameters.stream().filter(p -> p.getType().startsWith("com.")).collect(Collectors.toSet());
        for (Parameter p : parameters) {
            writeModelEntityClass(p, classWriter, basePath, basePackage);
        }


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
