# social-media-v2
This is bit enhanced version of simple social-media application( https://github.com/Nandlalaji/social-media ). i have used spring boot, H2, Spring data, Spring security and JWT to achieve backend of simple social-media application.

mvn spring-boot:run
Use above commond in cmd to start your application.

Following are the URL to use in this application
GET request 
localhost:8080/getAllPost

localhost:8080/getAllUser

localhost:8080/getUserNewFeed/{userId}    - userId is integer value

localhost:8080/getfollower/{followeeId}    - followeeId is integer value

Note - before i provide post request url, i should inform, i have used JWT so until you dont have JWT key you cant use post url. But you are good with get url with JWT

for generating JWT.
post following URL(use postman)
localhost:8080/authenticate
Header -  "Content-Type":"application/json"
Request body :
{
	"email":"testUser11@gmail.com",
	"password":"password11"
}

The above json request is already there in H2 default values. so once you start the sever this value will be there in DB.
You will get key in response.Use that in other post request header

Header - "Authorization":"Bearer keyfromRespone"

Remember key value in header should start with Bearer space.
the key is valid for 5 * 60 * 60. After that you have to generate key again.
POST URL-
localhost:8080/inputNewPost/{userId}/{content}    - no Request body. userId is integer and content is string value for post

localhost:8080/inputNewUser      - Request body should be json as follow
{"name":"provide your UserName","password":"provide your password","email":"provide email id"}

localhost:8080/follow/{followeeId}/{followerId} - no Request body, both followeeId and followerId are integer value

localhost:8080/unfollow/{followeeId}/{unfollowerId}  -no Request body, both followeeId and followerId are integer value


More about this application
I have use H2 DB and load some default value. so once you start the application and run above urls you will get some value. you can access H2 DB by

localhost:8080/h2-console
User Name: nand
Password: nandApplication

in resource you can find data.sql to see queries for default values. Also i have use file(~/data/demo) to store the value.So, if you keep on adding info and restart the server you will get the old data. But be careful the id should not be as in default data otherwise it will be override by default value. so use id not use in default id value in data.sql. Although i have use default increment of id. but this is incase you manipulate data in DB manually.

Also i used
spring.jpa.hibernate.ddl-auto=update
which mean it will create table by itself on first run from model class and only change if you change the model class.

I have used BCrypt to store password

Happy learning

planning to implement https and redis in this next.

