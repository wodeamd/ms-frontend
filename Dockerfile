FROM frolvlad/alpine-oraclejdk8:slim
ADD PROJECT_JAR app.jar
EXPOSE 8070
ENTRYPOINT ["java","-jar","app.jar"]