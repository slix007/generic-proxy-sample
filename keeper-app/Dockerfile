# linux/amd64 59.79 MB
FROM azul/zulu-openjdk-alpine:11-jre

COPY build/libs/keeper-app-0.0.1-SNAPSHOT.jar keeper-app.jar

EXPOSE 8080

#CMD ["/bin/sh","-c","java -jar /keeper-app.jar >> /var/log/keeper-app.log 2>&1"]
CMD ["java","-jar","/keeper-app.jar"]
