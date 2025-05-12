package org.openid.authzen.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class User {
    private String id;
    private String role;
    private String department;
    public User() {
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }  

}
