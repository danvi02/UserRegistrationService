FROM openjdk:17-alpine
ARG JAR_FILE=target/UserRegistrationService-1.1-SNAPSHOT.jar
ADD ${JAR_FILE} userregistration.jar
ENTRYPOINT ["java","-jar","/userregistration.jar"]