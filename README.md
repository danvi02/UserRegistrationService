# Controller API for Performance Testing

`In this GIT branch code, we will be making the communication between 2 docker containers using HOST
IP address. There are 2 ways in which we can make docker containers to communicate with each other.`

- **way 1:** Using Host machine IP(our system IP) if 2 or more docker containers want to communicate.

- **way 2** Create a docker compose file and define the services to make them connected to same network then containers can communicate with each 	other using container/service names.

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

### 2. Build docker image

- **docker build -t danvi/user-registration-service:1.1 --build-arg JAR_FILE=target/UserRegistrationService-1.1-SNAPSHOT.jar .**
-   `docker build`: This is the Docker command used to build an image from a Dockerfile.
-	`-t danvi/user-registration-service:1.1`: This specifies the name and optionally a tag to the 
	name of the image. In this case, the image will be tagged as danvi/user-registration-service with 
	version 1.1.
-	`--build-arg JAR_FILE=target/UserRegistrationService-1.1-SNAPSHOT.jar`: This option allows 
	passing a build-time variable to the Dockerfile. In this case, it sets the JAR_FILE build argument
	to target/UserRegistrationService-1.1-SNAPSHOT.jar.
-	`.`: This specifies the build context. It tells Docker to look for the Dockerfile and any files 
	it references in the current directory (.).

### 3. Run Docker Image

-	**docker compose up:** 
-	Run this command to pull mysql image and run it in docker container because this is required for our springboot application/image as it depends 	on this mysql image. Once it is UP and running then execute the below command to run your spring application image in docker container.

-	**docker run -p 9091:9999 -e SERVER.PORT=9999 danvi/user-registration-service:1.1 .**
-	`docker run` : This is the Docker command used to run a container from a Docker image.
-	`-p 9091:9999` : This option maps port 9999 from the container to port 9091 on the host system.
	It means that any traffic sent to port 9091 on the host will be forwarded to port 9999 inside 
	the container.
-	`-e SERVER.PORT=9999` : This option sets an environment variable SERVER.PORT with a value of 
	9999 inside the container. This environment variable likely configures the port on which the 
	application inside the container will listen for incoming connections. This `-e` is really helpful
	when you want to run the application in docker container on separate port.
-	`danvi/user-registration-service:1.1`: This specifies the name and tag of the Docker image from
	which to run the container. In this case, it is danvi/user-registration-service with version 
	1.1.
-	`.`: The dot at the end of the command is not necessary and might cause an error. It specifies
	the build context, but it is not applicable when running a container.

### 3. Push Docker Image to Repository(you need to create an account with docker hub to do this)

-	**docker push -t danvi/user-registration-service:1.1**
-	This command pushes the Docker image tagged as danvi/user-registration-service:1.1 to the 
	default container registry configured in your Docker environment. Make sure you are logged in 
	to the container registry before running this command, as you need the appropriate 
	permissions to push images.