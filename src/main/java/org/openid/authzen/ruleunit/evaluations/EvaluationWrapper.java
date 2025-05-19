package org.openid.authzen.ruleunit.evaluations;
import java.util.ArrayList;
import java.util.List;

public class EvaluationWrapper {

    private List<EvaluationResult> evaluations;
    public EvaluationWrapper() {
        this.evaluations = new ArrayList<EvaluationResult>();

    }

    public EvaluationWrapper(List<EvaluationResult> evaluations) {
        this.evaluations = evaluations;
    }

    public List<EvaluationResult> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<EvaluationResult> evaluations) {
        this.evaluations = evaluations;
    }

}
