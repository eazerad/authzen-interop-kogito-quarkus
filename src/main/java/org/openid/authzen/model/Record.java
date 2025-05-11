package org.openid.authzen.model;

public class Record {

    private String id;
    private String title;
    private String department;
    private String owner;
    public Record() {
    }
    public Record(String id, String title, String department, String owner) {
        this.id = id;
        this.title = title;
        this.department = department;
        this.owner = owner;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
}
