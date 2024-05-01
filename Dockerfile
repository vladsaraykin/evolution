FROM openjdk:18.0.2.1-oracle

COPY ./target/*.jar evolution.jar
EXPOSE 8080
ENTRYPOINT java $JAVA_OPTS -jar evolution.jar