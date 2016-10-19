package org.mule.modules.wsdl2connector.generator.model.entity;

import org.mule.modules.wsdl2connector.generator.model.ModeledClass;

import java.util.Collection;
import java.util.List;

import static org.mule.modules.wsdl2connector.generator.model.entity.Getter.generateGetters;
import static org.mule.modules.wsdl2connector.generator.model.entity.Setter.generateSetters;

public class ModelEntityClass extends ModeledClass {

    public List<Setter> setters;
    public List<Getter> getters;

    public ModelEntityClass(String className, String basePackage, List<String> setters, List<String> getters) {
        super(basePackage + "modelentity", className + "ModelEntity", (Collection<String>) null);
        this.setters = generateSetters(setters);
        this.getters = generateGetters(getters);
    }

    public List<Setter> getSetters() {
        return setters;
    }

    public void setSetters(List<Setter> setters) {
        this.setters = setters;
    }

    public List<Getter> getGetters() {
        return getters;
    }

    public void setGetters(List<Getter> getters) {
        this.getters = getters;
    }
}
