Silver Project Install/Run instructions:

###1. Technologies used: 
    Apache Maven 3.3.9
    JDK version 1.8.0_144
    Ubuntu Linux - Linux Version 4.10.0-37-generic
    Spring Boot 1.5.7.RELEASE
    H2 Database In-memory
    JPA
    JUnit 4.12
    Mockito

###2. To Run this project locally:

$ git clone https://github.com/likevarun/silver

$ mvn clean install spring-boot:run

Access http://localhost:8080/state/


###3. What is this repository for?

    The repository is the solution providing RESTful service covering business requirements for Silver Project.

###4. Assumptions/Improvements

-> Install REST client plugin on mozilla browser/ chrome browser and set Content-Type as application/json.
-> For POST call write JSON body as {"character" : "a", "amount" : "2"}
-> Added 10 test cases but can increase test coverage to check various business cases & -ve testing.
-> Use of Json Web Token authentication/authorization could further enhance security and user identification.
-> Implemented database connectivity using JPA and created table Silver to store key/value - userId/State.

