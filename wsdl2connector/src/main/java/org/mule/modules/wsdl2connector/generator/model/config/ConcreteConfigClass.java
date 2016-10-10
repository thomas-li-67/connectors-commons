package org.mule.modules.wsdl2connector.generator.model.config;

import org.mule.modules.wsdl2connector.generator.model.ModeledClass;

public class ConcreteConfigClass extends ModeledClass {

    private final BaseConfigClass baseConfigClass;
    private final String service;
    private final String clientRetrievalMethod;

    public ConcreteConfigClass(String name, BaseConfigClass baseConfigClass, String serviceFullyQualifiedName, String clientRetrievalMethod) {
        super(baseConfigClass.getBasePackage(), name, baseConfigClass.getImports());
        this.baseConfigClass = baseConfigClass;
        getImports().add(serviceFullyQualifiedName);
        this.service = serviceFullyQualifiedName.substring(serviceFullyQualifiedName.lastIndexOf(".") + 1);
        this.clientRetrievalMethod = clientRetrievalMethod;
    }

    public BaseConfigClass getBaseConfigClass() {
        return baseConfigClass;
    }

    public String getClient() {
        return baseConfigClass.getClient();
    }

    public String getService() {
        return service;
    }

    public String getClientRetrievalMethod() {
        return clientRetrievalMethod;
    }
}
