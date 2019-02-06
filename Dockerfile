FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY build/libs/*-all.jar moneytransfer.jar
CMD java ${JAVA_OPTS} -jar moneytransfer.jar