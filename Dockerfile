FROM openjdk:11
COPY ./plantio/target/plantio-0.0.1-SNAPSHOT.jar /app/
RUN mv /app/plantio-0.0.1-SNAPSHOT.jar /app/plantio.jar
WORKDIR /app/
CMD ["java", "-jar", "-Xss512K", "-Xmx100M", "-Dspring-boot.run.arguments=--jwtkey=blablamykeyblabla --weatherkey=4be19030b493c959dc1114b02b7a37cc --dbuser=diego --dbpass=ddb", "/app/plantio.jar"]
