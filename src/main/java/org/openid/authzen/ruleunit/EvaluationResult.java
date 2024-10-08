package org.openid.authzen.ruleunit;

public class EvaluationResult {
    private Boolean decision = false;

    public EvaluationResult() {
    }

    public EvaluationResult(Boolean decision) {
        this. decision = decision;
    }

    public Boolean getDecision() {
        return decision;
    }

    public void setDecision(Boolean decision) {
        this.decision = decision;
    }
}
