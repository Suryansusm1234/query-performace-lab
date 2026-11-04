# Step 1
FROM eclipse-temurin:21-jdk-ubi10-minimal AS builder

WORKDIR /build 
COPY gradlew settings.gradle* build.gradle* ./
COPY gradle gradle/
RUN ./gradlew dependencies --no-daemon

COPY src src/

RUN ./gradlew bootJar --no-daemon -x test

# Step 2
FROM eclipse-temurin:21-jre-noble
WORKDIR /app
COPY --from=builder /build/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]