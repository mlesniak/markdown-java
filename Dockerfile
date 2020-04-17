# We do not use a multi-stage build (here!) by design, since
# we want faster builds for the tradeoff of having to build
# locally / on the CI server itself.
#
# We have multiple solutions for the above problem, let's see
# which one will win.

FROM adoptopenjdk/openjdk11:latest

WORKDIR /app/data
ADD target/markdown-0.0.1-SNAPSHOT.jar /app/app.jar
ADD data /app/data

ENTRYPOINT ["java","-jar","/app/app.jar"]