# Docker File and Steps for Building, Running and Pushing Docker Images

FROM openjdk:17-alpine
ARG JAR_FILE=target/UserRegistrationService-1.1-SNAPSHOT.jar
ADD ${JAR_FILE} userregistration.jar
ENTRYPOINT ["java","-jar","/userregistration.jar"]

## Line 1: 
It will pull the openjdk image from docker and create a java runtime environment for our spring boot application

## Line 2:
This line defines an argument variable JAR_FILE with value of target/user-registration-service.jar. 
This argument can be overridden during the build process, allowing you to specify a different JAR file 
if needed like below.
- **To build docker image**
docker build -t danvi/user-registration-service:1.1 --build-arg JAR_FILE=target/UserRegistrationService-1.1-SNAPSHOT.jar . // it can be 1.0, 1.1 etc... 
In the above commands, the --build-arg option allows you to specify the value for the JAR_FILE build argument.
By changing the value of the JAR_FILE argument during the build process, you can build Docker images with 
different versions of the JAR file without modifying the Dockerfile itself.
## Line 3:
For instance, renaming user-registration-service.jar to userregistration.jar can make it more intuitive, 
especially in cases where the original file name is long or contains version information.
## Line 4:
This line specifies the default command to run when the container starts. It runs the Java application userregistration.jar without any additional parameters.

- **To run docker image**
	- if application.properties contains SERVER.PORT=7777, we need to run image as below because our springboot application will run on port 7777 in docker container.
	  docker run -p 9091:7777 -e SERVER.PORT=9999 danvi/user-registration-service:1.1 .
	  -p creates a firewall rule like only the requests coming from 9091 host machine should map to container running on port 7777.
	- if application.properties does not contain SERVER.PORT, by default 8080 port will be used by tomcat but if 8080 is already in use by some other services like Jenkins(runs on 8080) in that case our container will not run on 8080 port in such cases,
	- we can run our container on specific port(lets say 9999) by using below command
	  docker run -p 9091:9999 -e SERVER.PORT=9999 danvi/user-registration-service:1.1 .
	- The -e flag is used to set environment variables within the Docker container.
	  -p creates a firewall rule like only the requests coming from 9091 host machine should map to container running on port 9999.
	  
- ** Pushing docker image **
	- docker push danvi/user-registration-service:1.1
	- will push it docker image to docker hub with repository name as danvi/user-registration-service is the

# Docker Compose File

In a Dockerized environment, there can be multiple services running, each potentially in its own container. When we talk about services, we mean any application or process that provides specific functionality. In your case, you have two services:

- ** Spring Boot Application:** This is your application that needs to connect to the database.
- ** MySQL: ** This is the database service your application needs to communicate with.
When both your Spring Boot application and MySQL are running as Docker containers, they can communicate with each other using Docker's internal networking without needing to specify the host machine's IP address. Docker provides an internal DNS service that allows containers to find each other by service name (as specified in docker-compose.yml) or by container name.
So, if both your Spring Boot application and MySQL are running as containers in the same Docker network (as defined in your docker-compose.yml file), you can use the service name (mysqldb as per your configuration) as the hostname in your Spring Boot application's application.properties file:

- ** spring.datasource.url=jdbc:mysql://mysqldb:3306/registration **
- ** spring.datasource.username=dockeruser **
- ** spring.datasource.password=DanviShanmuki@2 **
- ** spring.jpa.hibernate.ddl-auto=update **
- ** spring.jpa.show-sql=true **

** In this context **

- ** Service running outside the Docker environment ** refers to a scenario where, for some reason, the MySQL ,
database is not running in a Docker container but directly on the host machine, outside the Docker 
environment.

- ** Connecting to MySQL running on the host machine itself ** refers to the situation where MySQL is not in 
a Docker container but installed and running directly on the host operating system, outside the Docker 
environment. In such a case, you would use the host machine's IP address in your Spring Boot application's 
configuration to establish a connection because the Docker containers cannot directly access services 
running outside the Docker environment without using the host machine's IP address.
