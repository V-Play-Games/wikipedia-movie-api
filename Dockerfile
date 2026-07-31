# ---------------------------------------------
# Stage 1 - Build the fat JAR with Gradle
# ---------------------------------------------
FROM gradle:9.6-jdk25 AS builder

WORKDIR /app

# Copy only the dependency-resolution files first (better layer caching)
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle

# Pre-fetch dependencies (cached unless the above files change)
RUN gradle dependencies --no-daemon || true

# Copy the rest of the source code
COPY src ./src

# Build the fat JAR (all dependencies bundled)
RUN gradle buildFatJar --no-daemon

# ---------------------------------------------
# Stage 2 - Lean runtime image
# ---------------------------------------------
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy the fat JAR from the builder stage
COPY --from=builder /app/build/libs/*-all.jar app.jar

# Expose the Ktor default port
EXPOSE 8080

# Start the server
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
