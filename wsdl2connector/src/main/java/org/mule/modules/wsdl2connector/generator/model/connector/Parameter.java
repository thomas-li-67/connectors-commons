package org.mule.modules.wsdl2connector.generator.model.connector;

import org.mule.modules.wsdl2connector.generator.model.ModeledMember;

import static java.lang.String.format;

/**
 * Created by gortiz on 08/10/16.
 */
public class Parameter extends ModeledMember {
    private String type;

    public Parameter(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return format("%s %s", type, getName());
    }
}
