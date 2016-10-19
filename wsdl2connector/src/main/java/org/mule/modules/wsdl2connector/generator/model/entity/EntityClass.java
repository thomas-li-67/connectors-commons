package org.mule.modules.wsdl2connector.generator.model.entity;

import org.apache.commons.lang.WordUtils;
import org.mule.modules.wsdl2connector.generator.model.ModeledClass;
import org.mule.modules.wsdl2connector.generator.model.connector.Parameter;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static org.apache.commons.lang.WordUtils.capitalize;

public class EntityClass extends ModeledClass {

    public List<Parameter> parameters;

    public EntityClass(String basePackage, Set<String> imports, String name, List<Parameter> parameters) {
        super(basePackage + ".entities", getEntityType(name), imports);
        this.parameters = parameters;
    }

    public static String getEntityType(String name){
       return capitalize(format("%sEntity", name));
    }


    public List<Parameter> getParameters() {
        return parameters;
    }

}
