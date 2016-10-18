package org.mule.modules.wsdl2connector.generator.model.connector;

import org.mule.modules.wsdl2connector.generator.io.ClassWriter;

import java.util.ArrayList;
import java.util.List;

import static org.mule.modules.wsdl2connector.generator.model.connector.EntityClassBuilder.writeEntityClass;

public class ProcessorBuilder {

    private final ConnectorClassBuilder connectorClassBuilder;
    private final String name;
    private final String returnType;
    private List<Parameter> parameterList = new ArrayList<>();

    public ProcessorBuilder(ConnectorClassBuilder connectorClassBuilder, String returnType, String name) {
        this.connectorClassBuilder = connectorClassBuilder;
        this.returnType = returnType;
        this.name = name;
    }

    public ConnectorClassBuilder addToConnector(ClassWriter classWriter) {
        this.parameterList = writeEntityClass(name, connectorClassBuilder.basePackage, parameterList, classWriter);
        return connectorClassBuilder.getMethodBuilder(new Processor(name, returnType, parameterList));
    }


    public ProcessorBuilder addParam(String type, String name) {
        parameterList.add(new Parameter(name, type));
        return this;
    }
}
