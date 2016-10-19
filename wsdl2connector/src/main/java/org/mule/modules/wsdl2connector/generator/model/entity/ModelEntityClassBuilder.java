package org.mule.modules.wsdl2connector.generator.model.entity;

import org.mule.modules.wsdl2connector.generator.io.ClassWriter;
import org.mule.modules.wsdl2connector.generator.model.connector.Parameter;
import org.mule.modules.wsdl2connector.generator.model.entity.ModelEntityClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;

public class ModelEntityClassBuilder {


    public static List<Parameter> writeModelEntityClass(Parameter parameter, ClassWriter classWriter, String basePath, String basePackage) throws FileNotFoundException {
        System.out.print(format("%s/%s.java", basePath, parameter.getType().replace(".", "/")));
        Scanner scanner = new Scanner(new File(format("%s/%s.java", basePath, parameter.getType().replace(".", "/"))));
        List<String> setters = new ArrayList<>();
        List<String> getters = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (!line.toLowerCase().contains("*")
                    && !line.matches(".*(return).*")
                    && !line.matches(".*(static).*")
                    && !line.matches(".*(getClassLoader).*")
                    && !line.matches(".*(interface).*")
                    ) {
                if (line.matches("(?i).*(set).*")) {
                    setters.add(line);
                }
                if (line.matches("(?i).*(get).*")) {
                    getters.add(line);
                }
            }
        }
        String[] modelEntityNameArray = parameter.getType().split("\\.");
        String modelEntityName = modelEntityNameArray[modelEntityNameArray.length - 1];
        ModelEntityClass m = new ModelEntityClass(modelEntityName, basePackage, setters, getters);
        classWriter.writeClass("ModelEntityClass", m);
        return null;
    }
}
