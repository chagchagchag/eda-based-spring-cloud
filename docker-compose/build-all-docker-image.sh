cd ..
./gradlew :spring-cloud:api-gateway:clean
./gradlew :spring-cloud:api-gateway:jibDockerBuild
./gradlew :spring-cloud:api-gateway:jib

./gradlew :spring-cloud:config-service:clean
./gradlew :spring-cloud:config-service:jibDockerBuild
./gradlew :spring-cloud:config-service:jib

./gradlew :spring-cloud:discovery-service:clean
./gradlew :spring-cloud:discovery-service:jibDockerBuild
./gradlew :spring-cloud:discovery-service:jib

./gradlew :user-service:user-container:clean
./gradlew :user-service:user-container:jibDockerBuild
./gradlew :user-service:user-container:jib

./gradlew :order-service:order-container:clean
./gradlew :order-service:order-container:jibDockerBuild
./gradlew :order-service:order-container:jib

./gradlew :catalog-service:catalog-container:clean
./gradlew :catalog-service:catalog-container:jibDockerBuild
./gradlew :catalog-service:catalog-container:jib
