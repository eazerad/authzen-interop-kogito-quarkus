package org.openid.authzen.ruleunit.search;

public class SearchResourceResult implements SearchResult {
    private String type;
    private String id;

    public SearchResourceResult() {
    }
    public SearchResourceResult(String type, String id) {
        this.type = type;
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
