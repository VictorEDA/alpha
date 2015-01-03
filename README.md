[![Build Status](https://travis-ci.org/VictorEDA/alpha.svg?branch=master)](https://travis-ci.org/VictorEDA/alpha)

# Name TBD

## Key Technologies
- Java 8
- Jersey 2 REST Server/Client (JAX-RS 2.0)
- Maven
- Hibernate (JPA 2.1)
- Derbey DB
- Cassandra and/or InfluxDB
- Spring (for configuration and transaction management)
 
## Requirements
### Ubuntu
- Java 8
  - `apt-get install -y software-properties-common`
  - `add-apt-repository ppa:webupd8team/java`
  - Press ENTER
  - `apt-get update -qq`
  - `apt-get install -y oracle-java8-installer`
  - Click Ok and Yes
  - Update `JAVA_HOME` environment variable to point to install directory, like: `JAVA_HOME=/usr/lib/jvm/java-8-oracle/`
- Maven
  - `apt-get install maven`

## Run Tests
`mvn test`

## Deploy
`mvn tomcat7:run -DskipTests=true`

This runs embedded Tomcat server on port 8080 using an embedded Derby DB. Verify deployment by doing:<br>
`curl -i localhost:8080/organizations/test`
