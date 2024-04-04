# authzen-interop-kogito-quarkus Project

This is a demo for the Authzen interop using an open source decision service for the PDP, Kogito: https://kogito.kie.org/
Kogito uses Drools as its rule engine, see https://www.drools.org/

The model and logic is all in DMN. The file is: resources/mytodo.dmn.
The generated documentation for the decision model is [here](doc/Documentation-evaluations.html)

The only java code is for the "PIP", to read the list of users as the interop scenario requires to look up user info. See org.openid.authzen.Users.java

To read the DMN file, if you are using VSCode, install the extension: https://docs.kogito.kie.org/latest/html_single/#proc-kogito-vscode-extension_kogito-creating-running

There are plenty of resources to learn about DMN and the FEEL expression language.
DMN and FEEL standards are maintained by the Object Management Group (OMG) group: https://www.omg.org/spec/DMN/

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
./mvnw clean package -Dnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:22.3.2-java17-amd64 -DskipTests
```

You can then execute your native executable with: `./target/authzen-interop-kogito-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.


## Provided Code

## Build docker image

Building the quarkus application (native executable). Assumes you followed the step to build the native executable above.

```shell script
docker build --platform linux/amd64 -f src/main/docker/Dockerfile.native-micro -t eazerad/authzen-interop:0.1-quarkus-native .
```

Then to run:
```shell script
docker run -i --rm -p 8080:8080 eazerad/authzen-interop:0.1-quarkus-native
```

## Related Guides

- Kogito ([guide](https://quarkus.io/guides/kogito)): Add business automation capabilities - processes and rules with Kogito (a toolkit that originates from projects Drools and jBPM)
