#FROM alpine

#ARG PROJECT
#RUN echo $PROJECT

FROM openjdk:8-slim
COPY ${PROJECT}-1.0.0.jar /
CMD java -jar ${PROJECT}-1.0.0.jar

#