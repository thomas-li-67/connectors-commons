package org.mule.modules.wsdl2connector.generator.io;

import org.mule.modules.wsdl2connector.generator.model.config.BaseConfigClass;
import org.mule.modules.wsdl2connector.generator.model.connector.ConnectorClass;
import org.mule.modules.wsdl2connector.generator.model.connector.ConnectorClassBuilder;
import org.mule.modules.wsdl2connector.generator.model.connector.ProcessorBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.lang.String.format;

public class ConnectorClassReader {

    private final String basePackage;

    public ConnectorClassReader(String basePackage) {
        this.basePackage = basePackage;
    }

    public ConnectorClass read(BaseConfigClass baseConfigClass, String basePath, String clientFullyQualifiedName) {
        try {
            Scanner scanner = new Scanner(new File(format("%s/%s.java", basePath, clientFullyQualifiedName.replace(".", "/"))));
            ConnectorClassBuilder connectorClassBuilder = null;
            ProcessorBuilder processorBuilder = null;
            ConnectorClass connectorClass = null;
            Set<String> imports = new HashSet<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (!line.startsWith("@") && !line.startsWith("package ") && !line.isEmpty() && !line.startsWith("/") && !line.startsWith("*")) {
                    if (line.startsWith("import ")) {
                        imports.add(line.substring(line.indexOf(" ") + 1, line.indexOf(";")));
                    } else {
                        if (line.startsWith("public ")) {
                            line = line.substring(7);
                            if (line.startsWith("class") || line.startsWith("interface")) {
                                connectorClassBuilder = new ConnectorClassBuilder(basePackage, imports, baseConfigClass, line.split(" ")[1]);
                            } else {
                                List<String> elements = Arrays.asList(line.split(" "));
                                processorBuilder = connectorClassBuilder.getMethodBuilder(extractImport(elements.get(0), imports), elements.get(1).replace("(", "").replace(");", ""));
                            }
                        } else {
                            if (!(line.startsWith("}") || line.startsWith(")"))) {
                                List<String> param = Arrays.asList(line.split(" "));
                                processorBuilder.addParam(extractImport(param.get(0), imports), param.get(1).replace(",", ""));
                            }
                        }
                        if (line.endsWith("}")) {
                            connectorClass = connectorClassBuilder.build();
                        } else {
                            if (line.endsWith(");")) {
                                processorBuilder.addToConnector();
                            }
                        }
                    }
                }
            }
            return connectorClass;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractImport(String fullyQualifiedName, Set<String> imports) {
        imports.add(fullyQualifiedName);
        return fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1);
    }
}
