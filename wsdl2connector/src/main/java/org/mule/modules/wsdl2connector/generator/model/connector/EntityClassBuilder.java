package org.mule.modules.wsdl2connector.generator.model.connector;

import com.google.inject.internal.util.Lists;
import org.mule.modules.wsdl2connector.generator.io.ClassWriter;
import java.util.List;

import static org.mule.modules.wsdl2connector.generator.model.connector.EntityClass.getEntityType;

public class EntityClassBuilder {

    public static List<Parameter> writeEntityClass(String processorName, String basePackage, List<Parameter> parameterList, ClassWriter classWriter) {
        classWriter.writeClass("EntityClass", new EntityClass(basePackage, null, processorName, parameterList));
        return Lists.newArrayList(new Parameter(processorName, getEntityType(processorName)));
    }

}
