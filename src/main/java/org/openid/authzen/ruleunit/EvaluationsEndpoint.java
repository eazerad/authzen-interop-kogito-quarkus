/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.openid.authzen.ruleunit;

import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/evaluations")
public class EvaluationsEndpoint {

    @Inject
    KieRuntimeBuilder kieRuntimeBuilder;

    @POST()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public EvaluationWrapper executeQuery(EvaluationsDto evalDto) {
        KieSession session = kieRuntimeBuilder.newKieSession();

        //List<Evaluation> evaluations = new ArrayList<Evaluation>();
        EvaluationWrapper wrapper = new EvaluationWrapper();
        session.setGlobal("evaluations", wrapper);

        evalDto.evaluations.forEach(session::insert);
        session.insert(evalDto.subject);
        session.insert(evalDto.action);
        session.insert(evalDto.context);

        //loanDto.getLoanApplications().forEach(session::insert);
        session.fireAllRules();

        //evaluations.add(new Evaluations());

        return wrapper;
    }
}
