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
EXPOSE 8081

# mvn clean install
# docker build  -t us.gcr.io/hondu-pf/ms-blockchain:0.1.2 -f Dockerfile .
# gcloud docker -- push us.gcr.io/hondu-pf/ms-blockchain:0.1.2
# git commit -am 'fix idproject'
# git push

