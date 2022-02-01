# Project
This is a simple vending machine REST API project. Used technologies are:
 - Java 11
 - Spring Boot + Spring Web
 - Lombok
 - Springdoc Openapi for documenting the endpoints

# Getting Started
To run the project you have two options:

- run `.\mvnw spring-boot:run` which will start an instance of the project;
- build project with `.\mvnw clean package` 
and execute the jar `java -jar .\target\vending-machine-0.0.1-SNAPSHOT.jar`

# Swagger
Project uses Springdoc Openapi which uses Swagger for generating
a description of the implemented REST endpoints.
To access the generated UI page go to [http://localhost:8080/api/v1/swagger-ui/index.html](http://localhost:8080/api/v1/swagger-ui/index.html) 