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

When the application is up/running then hit URL  http://localhost:8080/state/  on browser address bar. UserId will get appened on URL, capture the new URL.


###3. What is this repository for?

    The repository is the RESTful service solution covering business requirements for Silver Project.

###4. Assumptions/Improvements

-> To identify different browser as different user w.r.t to the computer/OS, the application is generating unique userId and performing URL re-writing to the browser. The UserId is uniqueId, do not share with other browser. Hit http://localhost:8080/state/ on other browser to get new userId. 
-> The User-Agent information also could have been used from getHeader("User-Agent") and then encrypt the User-Agent string in alphanumeric form generating userId to determine the identity but it has it's own short-comings. The similar computer/OS and browser combination can have same User-Agent information. 
-> Install REST client plugin on mozilla browser/ chrome browser and set Content-Type as application/json.
-> First hit http://localhost:8080/state on any address bar of browser, this will concatinate userId with URL.
-> Copy this URL and then open any rest client, then hit all the GET, POST, DELETE using URL appened userId as Param.
   e.g GET http://localhost:8080/state?userId=dbdhk0 POST http://localhost:8080/chars?userId=dbdhk0  
       DELETE http://localhost:8080/chars/a?userId=dbdhk0
-> For POST call write JSON body as {"character" : "a", "amount" : "2"}
-> Added 10 test cases but can increase test coverage to check various business cases & -ve testing.
-> Use of Json Web Token authentication/authorization could further enhance security and user identification.
-> Implemented database connectivity using JPA and created table Silver to store key/value - userId/State.

