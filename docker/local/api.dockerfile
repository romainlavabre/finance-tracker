FROM openjdk:17-slim

WORKDIR app
ENTRYPOINT ["./mvnw","spring-boot:run","-Dspring-boot.run.profiles=local"]