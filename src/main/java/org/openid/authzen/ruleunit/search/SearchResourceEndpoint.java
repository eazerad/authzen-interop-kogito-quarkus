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
package org.openid.authzen.ruleunit.search;

import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("search/resource")
public class SearchResourceEndpoint {
    private static Logger logger = LoggerFactory.getLogger(SearchResourceEndpoint.class);

    @Inject
    KieRuntimeBuilder kieRuntimeBuilder;

    /**
     * This endpoint is used to search for a resource.
     * @param searchDto the search DTO
     * @return the result wrapper
     */
    @POST()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultWrapper executeQuery(SearchDto searchDto) {
        KieSession session = kieRuntimeBuilder.newKieSession();

        ResultWrapper wrapper = new ResultWrapper();

        session.setGlobal("results", wrapper);

        // insert the type of search
        session.insert(new SearchType("resource"));

        // insert the input objects
        session.insert(searchDto.subject);
        session.insert(searchDto.action);
        session.insert(searchDto.resource);

        logger.info("Loaded subject, action and resource to working memory");

        // load the users
        Utils.getUsers().forEach(session::insert);
        // load the records
        Utils.getRecords().forEach(session::insert);
        logger.info("Loaded users and records into working memory");

        session.fireAllRules();


        return wrapper;
    }
}
