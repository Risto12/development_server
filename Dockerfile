FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

#FROM openjdk:11
#EXPOSE 8080:8080
#EXPOSE 8443:8443
#RUN mkdir /app
#COPY --from=build /home/gradle/src/build/libs/*.jar /app/
#ENTRYPOINT ["java","-jar","/app/com.development.ktor-development-server-all.jar"]
