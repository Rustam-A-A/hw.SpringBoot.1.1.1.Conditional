FROM openjdk:15-jdk-alpine
EXPOSE 8081
ADD build/libs/SpringBootConditional-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]
