package org.mule.modules.wsdl2connector.generator.model.config;

import org.mule.modules.wsdl2connector.generator.model.ModeledClass;

public class BaseConfigClass extends ModeledClass {

    private final String client;

    public BaseConfigClass(String basePackage, String clientFullyQualifiedName) {
        super(basePackage + ".config", "BaseConfig", clientFullyQualifiedName);
        this.client = clientFullyQualifiedName.substring(clientFullyQualifiedName.lastIndexOf(".") + 1);
    }

    public String getClient() {
        return client;
    }
}
