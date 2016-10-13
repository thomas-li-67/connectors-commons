package org.mule.modules.wsdl2connector;

import org.apache.maven.plugins.annotations.Parameter;

public class Configuration {

    @Parameter(property = "type")
    private AuthenticationType type;

    @Parameter(property = "basePath")
    private String basePath;

    @Parameter(property = "serviceClass")
    private String serviceClass;

    @Parameter(property = "clientRetrievalMethod")
    private String clientRetrievalMethod;

    @Parameter(property = "basePackage")
    private String basePackage;


    public AuthenticationType getType() {
        return type;
    }

    public void setType(AuthenticationType type) {
        this.type = type;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }


    public String getClientRetrievalMethod() {
        return clientRetrievalMethod;
    }

    public void setClientRetrievalMethod(String clientRetrievalMethod) {
        this.clientRetrievalMethod = clientRetrievalMethod;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
