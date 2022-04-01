FROM openjdk:8
EXPOSE 3020

ADD target/like-service.jar like-service.jar
ENTRYPOINT ["java","-jar","like-service.jar"]