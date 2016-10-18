package org.mule.modules.wsdl2connector.generator.model.connector;

import org.mule.modules.wsdl2connector.generator.model.ModeledMember;

import static java.lang.String.format;


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



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Parameter.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Parameter other = (Parameter) obj;

        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }

        if ((this.getName() == null) ? (other.getName() != null) : !this.getName().equals(other.getName())) {
            return false;
        }
            return true;

    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }


}
