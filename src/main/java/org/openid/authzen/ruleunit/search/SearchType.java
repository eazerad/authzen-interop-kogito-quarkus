package org.openid.authzen.ruleunit.search;

/**
 * SearchType tells the rule unit what type of search to perform.
 */
public class SearchType {
    private String type;

    public SearchType() {
    }
    public SearchType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
