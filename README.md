This is bit enhanced version of simple social-media application( https://github.com/Nandlalaji/social-media ). i have used spring boot, H2, Spring data, Spring security, JWT and https to achieve backend of simple social-media application. I have also used spring boot test and mockito to do junit testing using mvcMock.

Before we start the server, we need to get SSL certificate.SSL certificate is issued by a trusted Certificate Authority (CA). But for our learning purpose we can go with self signed certificate. For creating self signed certificate, you can follow command in src/main/resources/ssl.txt. I have already created it so no need to worry about it now

Also I have resolved n+1 hibernate issue for optimization. i have used modelMapper for generating JSON response.

For our testing we will we using Postman Api(not the web version, there is not option for ssl certification verification)

Though we are using postman api. But if we try GET request in web browser(chrome), it will show untrusted certificate(since self-certificate is not trusted in browser). you can click on advanced button(chrome) and accept the trust. Also when you hit Get URL with http://localhost:8080 it will route to https://localhost:8443 this is not the part of certificate but the spring security where tomcat is routing request to secure port.

use below command to start server.

mvn spring-boot:run

Open your Postman API. Go to Setting(option top right) -> General
You will find ssl certificate verification button on. Turn it off. 

Dont worry, Since i have loaded some data using data.sql while starting server. you will find result with below URL. Also i have used H2 file to store Data. so next when you start server it will only overwrite missing default value(used ON DUPLICATE KEY)

Following are the URL to use in this application 

GET request 


https://localhost:8443/getAllPost

https://localhost:8443/getAllUser

https://localhost:8443/getUserNewFeed/{userId} - userId is integer value

https://localhost:8443/getHomeNewFeed/{userId} - userId is integer value

https://localhost:8443//getFollowers/{followeeId} - followeeId is integer value

Note - before i provide post request url, i should inform, i have used JWT so until you dont have JWT key you cant use post url. But you are good with GET url without JWT key

for generating JWT. post following URL(use postman) https://localhost:8443/authenticate Header - "Content-Type":"application/json" Request body : { "email":"testUser11@gmail.com", "password":"password11" }

The above json request is already there in H2 default values. You will get key in response.Use that in other post/delete request header

Header -
	
	 "Authorization":"Bearer keyfromRespone"
		
	 "Content-Type": "application/json"

Remember key value in header should start with Bearer space. the key is valid for 5 * 60 * 60. After that you have to generate key again.


POST URL- 


https://localhost:8443/inputNewPost/{userId}/{content} - no Request body. userId is integer and content is string value for post

https://localhost:8443/inputNewUser - Request body should be json as follow {"name":"provide your UserName","password":"provide your password","email":"provide email id"}

https://localhost:8443/follow/{followeeId}/{followerId} - no Request body, both followeeId and followerId are integer value


DELETE URL - 

https://localhost:8443/removeUser/{userId}  - userId is integer value

https://localhost:8443/removePost/{postId}   -postId is integer value

https://localhost:8443/unfollow/{followeeId}/{unfollowerId} - both followeeId and followerId are integer value

More about this application I have use H2 DB and load some default value. so once you start the application and run above urls you will get some value. you can access H2 DB by

localhost:8080/h2-console User Name: nand

Password: nandApplication

in resource you can find data.sql to see queries for default values. Also i have use file(~/data/demo) to store the value.So, if you keep on adding info and restart the server you will get the old data. But be careful the id should not be as in default data otherwise it will be override by default value. so use id not use in default id value in data.sql. Although i have use default increment of id. but this is incase you manipulate data in DB manually.

Also i used

spring.jpa.hibernate.ddl-auto=update

which mean it will create table by itself on first run from model class and only change if you change the model class.

I have used BCrypt to store password 

Happy learning

planning to implement redis in this next.
