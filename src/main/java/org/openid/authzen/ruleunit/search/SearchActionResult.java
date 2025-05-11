package org.openid.authzen.ruleunit.search;

public class SearchActionResult implements SearchResult {
    private String name;
    
    public SearchActionResult() {
    }


    public SearchActionResult(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
