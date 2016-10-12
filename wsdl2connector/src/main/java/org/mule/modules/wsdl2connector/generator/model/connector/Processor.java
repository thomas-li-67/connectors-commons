package org.mule.modules.wsdl2connector.generator.model.connector;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.mule.modules.wsdl2connector.generator.model.ModeledMember;

import java.util.List;

import static com.google.common.collect.Lists.transform;

public class Processor extends ModeledMember {

    private String returnType;
    private List<Parameter> parameters;

    public Processor(String name, String returnType, List<Parameter> parameterList) {
        super(name);
        this.returnType = returnType;
        this.parameters = parameterList;
    }

    public String getParameterNames() {
       return Joiner.on(", ").join(transform(parameters, new Function<Parameter, String>() {

            public String apply(Parameter parameter) {
                return parameter.getName();
            }
        }));
    }

    public String getReturnType() {
        return returnType;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
