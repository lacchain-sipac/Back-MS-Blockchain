################### Stage 2: A minimal docker image with command to run the app 
FROM openjdk:8-jre-alpine
ENV APP_FILE ms-blockchain-1.0.0.jar

ENV APP_HOME /usr/apps

ENV APP_SOURCE ms-blockchain/target

ARG JAR_FILE=target/ms-blockchain-1.0.0.jar
COPY $JAR_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -Djava.security.egd=file:/dev/./urandom -jar $APP_FILE"]
EXPOSE 8092

# mvn clean install
# docker build  -t us.gcr.io/hondu-pf/ms-blockchain:1.3.4 -f Dockerfile .
# gcloud docker -- push us.gcr.io/hondu-pf/ms-blockchain:1.3.4

#mvn sonar:sonar -Dsonar.host.url=https://steps.everis.com/sonarqube  -Dsonar.login=852e97078744df9153150f35c85ca8cbc8efeb23