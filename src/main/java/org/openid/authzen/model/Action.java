package org.openid.authzen.model;

public class Action {
    private String name;

    public Action() {
    }

    public Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
