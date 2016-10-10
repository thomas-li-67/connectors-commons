package org.mule.modules.wsdl2connector.generator.model.connector;

import java.util.ArrayList;
import java.util.List;

public class ProcessorBuilder {

    private final ConnectorClassBuilder connectorClassBuilder;
    private final String name;
    private final String returnType;
    private final List<Parameter> parameterList = new ArrayList<>();

    public ProcessorBuilder(ConnectorClassBuilder connectorClassBuilder, String returnType, String name) {
        this.connectorClassBuilder = connectorClassBuilder;
        this.returnType = returnType;
        this.name = name;
    }

    public ConnectorClassBuilder addToConnector() {
        return connectorClassBuilder.getMethodBuilder(new Processor(name, returnType, parameterList));
    }

    public ProcessorBuilder addParam(String type, String name) {
        parameterList.add(new Parameter(name, type));
        return this;
    }
}
