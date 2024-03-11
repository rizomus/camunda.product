FROM openjdk:17
ADD target/product-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]