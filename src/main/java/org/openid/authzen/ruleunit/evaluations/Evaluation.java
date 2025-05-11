package org.openid.authzen.ruleunit.evaluations;

import org.openid.authzen.model.Resource;

public class Evaluation {
    private Resource resource;
    private Boolean decision = false;

    public Evaluation() {
    }

    public Evaluation(Resource resource, Boolean decision) {
        this.resource = resource;
        this. decision = decision;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Boolean getDecision() {
        return decision;
    }

    public void setDecision(Boolean decision) {
        this.decision = decision;
    }
}
