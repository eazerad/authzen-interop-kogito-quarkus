package org.openid.authzen.model;

import java.util.HashMap;
import java.util.Map;


public class Properties {
    private Map<String,Object> properties = new HashMap<>();

    public  Properties() {
    }

    
    public Properties(Map<String, Object> properties) {
        this.properties = properties;
    }
    public Properties(String key, String value) {
        this.properties.put(key, value);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }
    public String getProperty(String key) {
        return (String)this.properties.get(key);
    }
    public void removeProperty(String key) {
        this.properties.remove(key);
    }

}
