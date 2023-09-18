
# Dependencies stage
FROM maven:3.8.3-openjdk-17 as dependencies
COPY pom.xml /build/
WORKDIR /build/
RUN mvn --batch-mode dependency:go-offline dependency:resolve-plugins

# Build stage
FROM maven:3.8.3-openjdk-17 as build
# RUN --mount=type=cache,id=m2-cache,sharing=shared,target=/root/.m2  \
#   mvn --file /xyz/pom.xml package
COPY --from=dependencies /root/.m2 /root/.m2
COPY pom.xml /build/
COPY src ./build/src
WORKDIR /build/
RUN mvn -Dmaven.test.skip=true clean package

# Run stage
FROM openjdk:17-jdk-alpine as run
COPY --from=build /build/target/*.jar ./app.jar
#COPY /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

