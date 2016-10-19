package org.mule.modules.wsdl2connector.generator.model.entity;

import com.google.inject.internal.util.Lists;
import org.apache.commons.lang.StringUtils;
import org.mule.modules.wsdl2connector.generator.io.ClassWriter;
import org.mule.modules.wsdl2connector.generator.model.connector.Parameter;

import java.util.ArrayList;
import java.util.List;

import static org.mule.modules.wsdl2connector.generator.model.entity.EntityClass.getEntityType;

public class Setter {

    private String returnType;
    private String name;
    private List<Parameter> parameters;

    public Setter() {
    }

    public Setter(String returnType, String name, List<Parameter> parameters) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String p = StringUtils.join(parameters, ",");
        if (p == null || p.equals("")) {
            return String.format("public %s %s", returnType, name) + "()";
        } else {
            return String.format("public %s %s", returnType, name) + "(" + p + ")";

        }
    }

    public static List<Setter> generateSetters(List<String> setters) {
        List<Setter> setterList = new ArrayList<>();
        for (String s : setters) {
            Setter st = new Setter();
            String[] values = s.split(" ", 2);
            st.setReturnType(values[0]);
            st.setName(values[1].substring(0, values[1].indexOf("(")));
            String parameters = values[1].substring(values[1].indexOf("(") + 1, values[1].indexOf(")"));
            if (StringUtils.isNotEmpty(parameters)) {
                List<Parameter> parameterList = new ArrayList<>();
                for (String parameter : parameters.split(",")) {
                    values = parameter.split(" ");
                    parameterList.add(new Parameter(values[1], values[0]));
                }
                st.setParameters(parameterList);
            }
            setterList.add(st);
        }
        return setterList;
    }


    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public static class EntityClassBuilder {

        public static List<Parameter> writeEntityClass(String processorName, String basePackage, List<Parameter> parameterList, ClassWriter classWriter) {
            classWriter.writeClass("EntityClass", new EntityClass(basePackage, null, processorName, parameterList));
            return Lists.newArrayList(new Parameter(processorName, getEntityType(processorName)));
        }

    }
}
