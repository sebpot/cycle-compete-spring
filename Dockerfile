FROM amazoncorretto:17-alpine3.21

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN sed -i 's/\r$//' mvnw

RUN ./mvnw package

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=docker", "target/cyclecompete-0.0.1-SNAPSHOT.jar"]
