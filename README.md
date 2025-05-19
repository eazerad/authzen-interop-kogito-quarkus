# Implementing the AuthZEN API for Apache KIE (Kogito + Drools decision service)

This repository demonstrates how to implement the AuthZEN Authorization API 1.0 – draft 03 for decision services using the Apache KIE project (Kogito + Drools). 
The decision service can run in a Docker container and Quarkus + GraalVM were used for native compilation and quick startup.

See [AuthZEN draft API](https://openid.github.io/authzen/)  
See [Apache KIE](https://kie.apache.org/)

## Before we start
This is a proof of concept (POC).  
I recommend starting with your requirements to determine what type of authorization better fits your need: (e.g. ABAC, ReBAC, PBAC, etc.), and  evaluate the different options, open source or not. Consider your requirements (security, performance, etc.), including what type of resource you need to protect, if you need to store a large set of user permissions, etc.

## Why?
I developed this project in my free time to get familiarize with AuthZEN, add diversity by providing a vendor neutral voice.  
I'm no Apache KIE expert (or at least not yet) but I do have some expertize with Business Rules Management Systems (BRMS) as I worked in that space before landing in IAM and worked on ILOG JRules (now IBM ODM).  
I believe the AuthZEN API can be leveraged not only by core authorization products and API gateways, but also by general-purpose business rule engines, which are often used for authorization or personalization and are highly capable. There should be no reason not to broaden the horizon for the API and limit it.  
For example, Drools (an open-source BRMS) has been included in at least one Identity Governance and Administration (IGA) product to help compute dynamic attributes or Separation of Duties (SoD) policies and there is no reason a BRMS can't be used for coarse or fine grained access control decisions.

## BRMS are not specialized for Authorization policies

You may have policies that go beyond simple access control:

>- VIP users get access to a private area on our website. **(1)**
>- VIP users are defined as those who spend more than X dollars in a year and meet certain criteria. **(2)**
>- There are 3 VIP tiers: bronze, gold and platinum. Bronze is the entry level (more than X dollars). Gold is between X and Y dollars and Platinum is over Y dollars.  
>- Bronze VIPs get a discount of 5 percent on the last transaction of the quarter, Gold VIPs get 10% and Platinum 15%.  **(3)**


**(1)** is an access policy.  
**(2)** is a policy, which can be implemented as multiple business rules to determine if a user qualifies as a VIP. This determination is typically not made by an access policy, but is instead considered an input. In the ABAC model, it could be retrieved via a PIP (Policy Information Point) if the information is not part of the input and information about the subject.  
**(3)** relates to transaction and pricing decisions and would likely not be needed by an authorization service unless there are authorization decisions based on transaction amounts.

A general-purpose decision service like Drools can handle all these types of policies. For access control, It would fit under PBAC (Policy Based Access Control) if we look at the [IDPRO authorization model taxonomy](https://idpro.org/a-taxonomy-of-modern-authorization-models/)

In an enterprise environment, such decisions may require data from multiple systems, decision services/microservices and APIs. While some specialized authorization products can handle this, they tend to focus on specific domain decisions, such as `Can user X access resource Y?`. Business rules engines offer flexibility and could be used for other types of decisions or computations. They are often used to implement decisions in business processes.

## Identiverse 2025 AuthZEN interop and Search API

![AuthZEN and Apache KIE](doc/images/authzen-drools.png?raw=true "AuthZEN and Apache KIE")

### Context
We use the Apache KIE project, home to the most popular open-source business automation technologies: https://kie.apache.org/  
These technologies were recently moved to the Apache Foundation. Despite being a project in incubation, Drools is a well-known open-source business rule engine and has been available for decades. It is bundled in many enterprise products.

Some in the IAM space may wonder why integrate a tool not primarily designed for access control. The AuthZEN API’s goal is not to promote one xBAC model over another (ABAC, ReBAC, PBAC), but to provide a standard API that can be easily integrated in various ways (natively, via extension, proxy, etc.).

Drools has a broader scope than just authorization and access control; it is a general-purpose business rule engine. Use cases include credit scoring, loan validation, claims processing, insurance underwriting, and more. For authorization, it aligns with the PBAC model. It does not provide its own storage for anything other than the policies and model and specific consideration need to be applied to load or lookup external data. It does support stateless and stateful execution, event driven architecture, etc.

Specifically, we use Kogito and Drools for the decision service. Kogito provides everything needed to run cloud-ready applications.


# Let's get into the weeds

## Integration of the AuthZEN API
I'm planning to use DMN for everything in a future attempt (less code, easier for business users) but have focussed on implementing the authorization logic in technical rules (DRL) for the Search API. My goal is to decouple as much as possible the front-end AuthZEN API from the authorization logic so this project can be reused as a template and make it easier to implement AuthZEN with Kogito and Drools.  

For the Search APIs, the  Java model (aka POJO classes) for the input/output objects is under `scr/main/java/org/openid/authzen/model`  
The code to expose the 3 search endpoints is in `scr/main/java/org/openid/authzen/ruleunit/search`  
If you look at the subject search API REST endpoint `/Users/elieaz/code/authzen-interop-kogito-quarkus/src/main/java/org/openid/authzen/ruleunit/search/SearchSubjectEndpoint`, you will see that we insert the subject, action and resource in the working memory so the rule engine,Drools, can pick it up.  
The users and resources (loaded from an external database, here for simplicity we load from JSON files) are also inserted in the working memory. Note that we would prefer to have the objects passed as input to the decision service, it is not a good idea to do look up at runtime as it will impact performance.
The rules for all 3 search endpoints are defined in: `/Users/elieaz/code/authzen-interop-kogito-quarkus/src/main/resources/search.drl`
The authorization logic will have to be modified depending on the specific requirements (here we focused on the interop use cases).


## Pre-requisites

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

To build the project, you need Java 17, maven 3.8 and Docker (optional). Docker is convenient to build the native images without having GraalVM installed.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Or to build for amd64 if you are on Apple silicon:
```shell script
./mvnw clean package -Dnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:jdk-24.0.1-amd64	 -DskipTests
```

You can then execute your native executable with: `./target/authzen-interop-kogito-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.


## Provided Code

## Build docker image

Building the quarkus application (native executable). Assumes you followed the step to build the native executable above.

```shell script
docker build --platform linux/amd64 -f src/main/docker/Dockerfile.native-micro -t your_image .
```

Then to run:
```shell script
docker run -i --rm -p 8080:8080 your_image
```

## Related Guides

- Kogito ([guide](https://quarkus.io/guides/kogito)): Add business automation capabilities - processes and rules with Kogito (a toolkit that originates from projects Drools and jBPM)

# Appendix

## Prior AuthZEN interops
In the previous AuthZEN interops, we focused on the Access Evaluation and Evaluations endpoints. We first used a simple DMN model (See appendix for DMN) built with the Kogito DMN plugin in Visual Studio Code, and also demonstrated a DRL decision service for the Evaluations API endpoint.  
With Kogito, most of the REST API work is handled for us, and the microservice can be easily containerized using Quarkus (a Kubernetes-native Java stack). The latter approach allows making multiple decisions at once. The focus of the Identiverse 2025 interop is the Search API, which is based on the Evaluation model but aims to answer different questions.

For the Evaluation API, most of the work was done using the DMN editor without requiring coding. We defined the model for the Evaluation API (subject, action, resource). The only code needed was required to lookup the additional user data (getUser) and define the if a user has a role. The decision is made using a decision table and returns whether the decision (subject is granted "action") is true or false.  
What made it very simple is that Kogito creates by default a REST API with Json payload and the input/output required by the AuthZEN Evaluation API didn't require any addition. 


![DMN](doc/images/evaluation-dmn.png?raw=true "Evalution DMN")

![Decision Table](doc/images/decisiontable.png?raw=true "Decision Table")


The model and logic are all in DMN, in the file: `resources/evaluation.dmn`.  
Generated documentation for the decision model is [here](https://htmlpreview.github.io?https://github.com/eazerad/authzen-interop-kogito-quarkus/blob/main/doc/Documentation-evaluations.html).

The only Java code is for the "PIP", which reads the list of users as required by the interop scenario. See `org.openid.authzen.Users.java`.

To read the DMN file in VSCode, install the "Kogito Bundle" extension. More details are in the Kogito documentation: https://docs.kogito.kie.org/latest/html_single/#proc-kogito-vscode-extension_kogito-creating-running

There are many resources for learning DMN and the FEEL expression language.  
DMN and FEEL standards are maintained by the Object Management Group (OMG): https://www.omg.org/spec/DMN/


## More about the Tech used
### Kogito
"[Kogito](https://docs.kogito.kie.org/latest/html_single/#_cloud_first_priority) is a cloud-native business automation technology for building cloud-ready business applications.[...]"
Here is a list of compatible technologies:
![Kogito](doc/images/kogito-related-technolgies_enterprise.png?raw=true "Kogito")

## Drools
[Drools](https://www.drools.org/) is a business rule management system (BRMS). 
BRMS contains everything to define, deploy, execute and monitor the rules. Management is typically centralized and there are multiple options to deploy and execute policies, either embedded in a process or using an external execution server.
BRMS are an evolution of expert systems and one of the main execution algorithm associated with them is the [RETE](https://en.wikipedia.org/wiki/Rete_algorithm) algorithm.
For this project, we use both rules defined in Drools Rule Language (DRL), which are technical rule, work on a Java object model and require to do a bit more coding to define the model and its execution.
We also used a [DMN](https://www.omg.org/dmn/) (Decision Model and Notation) decision for one of the endpoint (the AuthZEN Evaluation endpoint). DMN is a standard for modeling and describing business decision. Drools offers an implementation and Kogito let us execute the DMN decision service with little need for coding outside of loading external data. DMN is more recent than BPMN that precedes it for Business Process Management (BPM).
