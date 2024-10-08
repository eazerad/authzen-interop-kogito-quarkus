package org.openid.authzen.ruleunit;

public class Resource {
    private String id;
    private String type;
    private EvalProperties properties;

    public Resource() {
    }

    public Resource(String id, String type, EvalProperties properties) {
        this.id = id;
        this.type = type;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EvalProperties getProperties() {
        return properties;
    }

    public void setProperties(EvalProperties properties) {
        this.properties = properties;
    }


}
