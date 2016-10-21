package org.mule.modules.wsdl2connector.generator.model.connector;

import org.mule.modules.wsdl2connector.generator.model.config.BaseConfigClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ConnectorClassBuilder {
    private static final List<String> EXCLUDED_IMPORTS = Arrays.asList("void", "boolean", "int", "long", "short", "float", "double", "String");
    public final String basePackage;
    private final String name;
    private final List<Processor> processors = new ArrayList<>();
    private final BaseConfigClass baseConfigClass;
    private final Set<String> imports;

    public ConnectorClassBuilder(String basePackage, Set<String> imports, BaseConfigClass baseConfigClass, String name) {
        this.basePackage = basePackage;
        this.imports = imports;
        this.name = name;
        this.baseConfigClass = baseConfigClass;
    }

    public ProcessorBuilder getMethodBuilder(String returnType, String name) {
        return new ProcessorBuilder(this, returnType, name);
    }

    public ConnectorClassBuilder getMethodBuilder(Processor modeledMethod) {
        processors.add(modeledMethod);
        return this;
    }

    public ConnectorClass build() {
        imports.removeAll(EXCLUDED_IMPORTS);
        return new ConnectorClass(basePackage, imports, name, baseConfigClass, processors);
    }
}
