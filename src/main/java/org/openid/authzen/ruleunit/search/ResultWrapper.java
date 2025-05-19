package org.openid.authzen.ruleunit.search;
import java.util.ArrayList;
import java.util.List;


public class ResultWrapper {

    private List<SearchResult> results;
    public ResultWrapper() {
        this.results = new ArrayList<SearchResult>();

    }

    public ResultWrapper(List<SearchResult> results) {
        this.results = results;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }

}
