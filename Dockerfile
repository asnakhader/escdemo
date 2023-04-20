FROM openjdk:17
ADD target/ecs-0.0.1-SNAPSHOT.jar ecs.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","ecs.jar"]