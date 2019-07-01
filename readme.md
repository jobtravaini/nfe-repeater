# nfe-repeater

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

The nfe-repeater is a microservice which will consume [nfe_received](https://docs.arquivei.com.br/?urls.primaryName=Arquivei%20API#/nfe/get_v1_nfe_received) api from Arquivei and store it on a relational database to reproduce its output as a proxy.

## Tech

nfe-repeater uses the following technologies:
* [spring-boot](https://spring.io/projects/spring-boot) - Java framework to build standalone applications
* [docker](https://www.docker.com/) - Framework to securely build and distrubute applications
* [h2-database](https://www.h2database.com/html/main.html) - Prototype friendly database
* [maven](https://maven.apache.org/) - Dependency manager and build tool for Java

## Installation

To install this application, you must have Maven and Docker installed on your machine. 

If you do not have Docker installed, the following plugin should be commented out of your [pom.xml](https://github.com/jobtravaini/nfe-repeater/blob/master/pom.xml) build definition:

```xml
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>dockerfile-maven-plugin</artifactId>
    <version>${dockerfile.version}</version>
    <executions>
        <execution>
            <id>default</id>
            <goals>
                <goal>build</goal>
                <goal>push</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <buildArgs>
            <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
        </buildArgs>
    </configuration>
</plugin>
```

To install all dependencies, generate the application runnable jar and build a docker image and push it to your docker, run the following command:

```sh
mvn clean install
```

To just run the application based on the project - without installing - run the following command:

```sh
mvn spring-boot:run
```

## Development Considerations

The application h2-database was choosen by its prototype nature. Per default, the database will behave as an in-memory instance which will lose/delete all data when the application is stopped. This behavior can be changed by configuring the [application.properties](https://github.com/jobtravaini/nfe-repeater/blob/master/src/main/resources/application.properties) file from Spring framework. Adding the following line will change the behavior of h2-database to persist the data on disk.

```properties
spring.datasource.url=jdbc:h2:file:/data/nfe-repeater
```

## Docker

When the installation is complete, an image will be built and available to create a container on your machine. To bring it up, run the command below completing the name and the image_hash with the maven build generated hash output.

```sh
docker run -d -p 8080:8080 <name> <image_hash>
```

## RESTful Service Documentation

___

### nfe

Return the details of a given nfe based on the access key provided.

**URL** : `/nfe`

**Method** : `GET`

**Parameters**:
* `key`: **String** - _query_

**Auth required** : NO

**Permissions required** : None

**Request example**: `http://localhost:8080/nfe?&key=35180104710149000115550010000084031000084036`

#### Success Response

**Code** : `200 OK`

**Content examples**

For an valid access key, the output will be a raw text version of xml encoded in base64

```text
PG5mZVByb2MgdmVyc2FvPSIzLjEwIiB4bWxucz0iaHR0cDovL3d3dy5wb3J0YWxmaXNjYWwuaW5mLmJyL25mZSI+PE5GZSB4bWxucz0iaHR0cDovL3d3dy5wb3J0YWxmaXNjYWwuaW5mLmJyL25mZSI+PGluZk5GZSB2ZXJzYW89IjMuMTAiIElkPSJORmUzNTE4MDEwNDcxMDE0OTAwMDExNTU1MDAxMDAwMDA4NDAzMTAwMDA4NDAzNiI+PGlkZT48Y1VGPjM1PC9jVUY+PGNORj4wMDAwODQwMzwvY05GPjxuYXRPcD5WRU5EQSBBIENPTlNVTUlET1I8L25hdE9wPjxpbmRQYWc+MDwvaW5kUGFnPjxtb2Q+NTU8L21vZD48c2VyaWU+MTwvc2VyaWU+PG5ORj44NDAzPC9uTkY+PGRoRW1pPjIwMTgtMDEtMzBUMDA6MDA6MDAtMDI6MDA8L2RoRW1pPjxkaFNhaUVudD4y...
```

___

### resync

Resynchronizes the database with arquivei nfe_received api

**URL** : `/resync`

**Method** : `post`

**Auth required** : NO

**Permissions required** : None

#### Success Response

**Code** : `200 OK`

___
