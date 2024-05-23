FROM openjdk:17

ADD /target/restaurant_collection-0.0.1-SNAPSHOT.jar restaurant_collection.jar

ENTRYPOINT ["java", "-jar", "restaurant_collection.jar"]