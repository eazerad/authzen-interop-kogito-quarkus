package org.openid.authzen.ruleunit;

public class EvalProperties {
    private String ownerID;

    public EvalProperties() {

    }
    public EvalProperties(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}
