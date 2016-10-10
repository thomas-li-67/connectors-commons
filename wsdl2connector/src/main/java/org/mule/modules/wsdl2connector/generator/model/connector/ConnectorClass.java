package org.mule.modules.wsdl2connector.generator.model.connector;

import org.mule.modules.wsdl2connector.generator.model.ModeledClass;
import org.mule.modules.wsdl2connector.generator.model.config.BaseConfigClass;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;

public class ConnectorClass extends ModeledClass {

    private final List<Processor> processors;
    private final BaseConfigClass baseConfigClass;

    public ConnectorClass(String basePackage, Set<String> imports, String name, BaseConfigClass baseConfigClass, List<Processor> processorList) {
        super(basePackage, format("Abstract%sConnector", name), imports);
        this.baseConfigClass = baseConfigClass;
        this.processors = processorList;
    }

    public List<Processor> getProcessors() {
        return processors;
    }

    public BaseConfigClass getBaseConfigClass() {
        return baseConfigClass;
    }
}
