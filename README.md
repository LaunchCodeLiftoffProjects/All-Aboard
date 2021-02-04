# All-Aboard

All Aboard is a social network gaming app designed to allow users to connect with other gamers with like-minded interests with local clubs. Users will be able to use the map feature to filter by distance and game categories.

The idea stemmed from a love of playing games. At times, there is no one to play with, not enough players, or you just want some fresh faces.


## Table of Contents
- [Technologies Used](#technologies-used)
- [Features](#features)
- [How to Run This Repo](#how-to-run-this-repo)


## Technologies Used
- Java 15
- Spring Boot
- Spring Security
- Thymeleaf
- MySQL
- Bootstrap


## Features
- User Profile Creation - Allows users to create a password protected account.
- Game Groups - Allows users to create and join individual gaming groups.
- User Roles - Allows for game groups to be moderated by an administrator of the group.
- Map Feature - Allows users to see game groups in their local area.
- Filtering - Allows users to filter the results on the map by distance, game being played, and whether the group is accepting members.
- Commenting/Messaging - Allows users to comment on game groups to coordinate.


## How to Run This Repo
1. Clone or download repo to your local machine.
2. Set up a MySQL schema with the name of your choice. Set up a user with access to the schema.
3. Open the project in your IDE of choice and navigate to Study-App\src\main\resources\application.properties
4. Update the top of the file with your chosen username, password, and schema name & location :
        
        spring.datasource.url=jdbc:mysql://localhost:3306/schema_name
        spring.datasource.username=user
        spring.datasource.password=greatpassword
    
5. Check the log to for the port the project is running on with Tomcat. The Tomcat message will look like this:
   
    ```2020-07-31 17:05:23.658  INFO 11332 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''```

6. Navigate to the mentioned port in the browser.

[Back To Top](#all-aboard)
