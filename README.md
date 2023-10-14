# User Registration Service with Docker

## Regarding Dockerfile Description

- **FROM openjdk:17-alpine**: This line specifies the base image for the Docker image. In this case, it's 	using the Alpine Linux-based OpenJDK 17 image.

- **ARG JAR_FILE=target/UserRegistrationService-1.1-SNAPSHOT.jar**: This line declares an argument 	named 	JAR_FILE and sets its default value to target/UserRegistrationService-1.1-SNAPSHOT.jar. This 	argument 	will be used later in the Dockerfile. When we run the below command:
	`docker build -t danvi/user-registration-service:1.1 --build-arg 	JAR_FILE=target/UserRegistrationService-1.1-SNAPSHOT.jar .` // it can be 1.0, 1.1, 1.2 etc... 
-	In the 	above command, the --build-arg option allows you to specify the value for the JAR_FILE 	build 	argument. By changing the value of the JAR_FILE argument during the build process, you can 	build 	Docker images with different versions of the JAR file without modifying the Dockerfile itself.

- **ADD ${JAR_FILE} userregistration.jar**: This line copies the JAR file specified by the JAR_FILE 	argument from the build context into the Docker image and renames it to userregistration.jar.

- **ENTRYPOINT ["java","-jar","/userregistration.jar"]**: This line sets the default command to run when the
	container starts. It runs the Java application inside the container using the java -jar command 	and 	specifies the userregistration.jar file as the application JAR.

## `In this GIT branch code, we will be making the communication between 2 docker containers 	docker compose`

- **way 1:** Using Host machine IP(our system IP) if 2 or more docker containers want to communicate.

- **way 2** Create a docker compose file and define the services to make them connected to same network then 	containers can communicate with each 	other using container/service names.

## Docker Images

- **1st docker image:** When we run springboot application image docker image, it becomes a 
    container and it has its own HOST IP and localhost specific to this container itself(lets 
	say this is user-registration-service container).

- **2nd docker image:** When we run mysql image which was pulled from docker cloud officially, 
	it becomes a container and it has its own HOST IP and localhost specific to this container 
	itself(lets say this is mysql container).


## Docker Commands

### 1. Build the application before building an image

- **mvn clean install**
-	This command builds the application and generates the jar file in target folder.

### 3. Build and Run Docker Image with `docker compose`

-	**docker compose up --build**: This command will execute three steps.
-	1.This will build image for user-registration-service
-	2.It will pull mysql image and make it run in docker container
-	3.Starts the springboot application in docker container
-	The below 2 command for build and run is not required if we run the above docker compose command as it has 2 services 	and it will build and start mysql, springboot as containers automatically.
-	**docker build -t danvi/user-registration-service:1.1 --build-arg 	JAR_FILE=target/UserRegistrationService-1.1-SNAPSHOT.jar .**
-	**docker run -p 9091:9999 -e SERVER.PORT=9999 danvi/user-registration-service:1.1 .**

### 3. Push Docker Image to Repository(you need to create an account with docker hub to do this)

-	**docker push -t danvi/user-registration-service:1.1**
-	This command pushes the Docker image tagged as danvi/user-registration-service:1.1 to the 
	default container registry configured in your Docker environment. Make sure you are logged in 
	to the container registry before running this command, as you need the appropriate 
	permissions to push images.