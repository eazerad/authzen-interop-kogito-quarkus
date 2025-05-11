package org.openid.authzen.ruleunit.evaluations;

import java.util.List;

import org.openid.authzen.model.Action;
import org.openid.authzen.model.Context;
import org.openid.authzen.model.Subject;

public class EvaluationsDto {
    public Subject subject;
    public Action action;
    public Context context;
    public List<Evaluation> evaluations;


}
