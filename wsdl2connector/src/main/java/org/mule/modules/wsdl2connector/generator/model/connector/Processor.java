package org.mule.modules.wsdl2connector.generator.model.connector;

import org.mule.modules.wsdl2connector.generator.model.ModeledMember;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class Processor extends ModeledMember {

    private String returnType;
    private List<Parameter> parameters;
    private Parameter unifiedParameter;

    public Processor(String name, String returnType, List<Parameter> parameterList, Parameter unifiedParameter) {
        super(name);
        this.returnType = returnType;
        this.parameters = parameterList;
        this.unifiedParameter = unifiedParameter;
    }

    public Parameter getUnifiedParameter() {
        return unifiedParameter;
    }

    public String getParameterNames() {
        return parameters.stream().map(Parameter::getName).collect(joining(", "));
    }

    public String getReturnType() {
        return returnType;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
