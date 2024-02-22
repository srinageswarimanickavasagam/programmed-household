# Stage 1: Build the parent POM and all its modules
FROM maven:3.8.4-openjdk-17-slim AS build-all
WORKDIR /app

# Copy the entire project directory
COPY . .

# Build the parent POM and all its modules
RUN mvn -f pom.xml clean install -DskipTests

# Stage 2: Create the backend image
FROM maven:3.8.4-openjdk-17-slim AS backend-img
WORKDIR /app
COPY --from=build-all /app/target/programmed-household-backend-1.0.0.RELEASE.jar ./target/
EXPOSE 5000
CMD ["java", "-jar", "./target/programmed-household-backend-1.0.0.RELEASE.jar"]
