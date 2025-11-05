# --- Stage 1: Build the application using Maven ---
# We use a Maven image that includes JDK 17 to compile our Java code.
# We name this stage "build" so we can refer to it later.
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file first to leverage Docker's layer caching.
# If the dependencies haven't changed, Docker won't re-download them on subsequent builds.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the application, skipping the tests for a faster build.
RUN mvn clean package -DskipTests

# --- Stage 2: Create the final, lightweight runtime image ---
# We use a slim OpenJDK image which is much smaller than the Maven image.
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the executable JAR file from the "build" stage into our final image.
COPY --from=build /app/target/maersk-booking-api-0.0.1-SNAPSHOT.jar app.jar

# Expose port 9090, which is the port our Spring Boot application listens on.
EXPOSE 9090

# The command to run when the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]