FROM gradle:jdk11 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble

FROM openjdk:11-jre-slim
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar
#change time zone if you want
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Duser.timezone=Europe/Madrid -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]