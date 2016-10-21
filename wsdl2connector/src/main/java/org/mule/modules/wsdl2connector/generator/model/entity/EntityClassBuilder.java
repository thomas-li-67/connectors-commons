package org.mule.modules.wsdl2connector.generator.model.entity;

import org.mule.modules.wsdl2connector.generator.io.ClassWriter;
import org.mule.modules.wsdl2connector.generator.model.connector.Parameter;

import java.util.List;

import static org.mule.modules.wsdl2connector.generator.model.entity.EntityClass.getEntityType;

public class EntityClassBuilder {

    public static Parameter writeEntityClass(String processorName, String basePackage, List<Parameter> parameterList, ClassWriter classWriter) {
        classWriter.writeClass("EntityClass", new EntityClass(basePackage, null, processorName, parameterList));
        return new Parameter(processorName, getEntityType(processorName));
    }

}
