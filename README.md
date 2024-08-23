# Spring Boot Testbed Application

This is a playground application for Spring Boot. 
It is a simple RESTful web service that takes advantage of Spring Boot 3 tooling to startup a service without the need for a docker compose file. 
This demonstrates how to split actuator endpoints and configuration for sending metrics to DataDog.

## Running the application

To run the application via IntelliJ, you can execute the `main` method in the `TestApplication` class under the test module.
This will start the necessary docker containers and the application.

