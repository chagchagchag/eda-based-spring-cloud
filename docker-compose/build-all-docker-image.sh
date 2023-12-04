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

./gradlew :foobar-user:foobar-user-container:clean
./gradlew :foobar-user:foobar-user-container:jibDockerBuild
./gradlew :foobar-user:foobar-user-container:jib

./gradlew :foobar-order:foobar-order-container:clean
./gradlew :foobar-order:foobar-order-container:jibDockerBuild
./gradlew :foobar-order:foobar-order-container:jib

./gradlew :foobar-display:foobar-display-container:clean
./gradlew :foobar-display:foobar-display-container:jibDockerBuild
./gradlew :foobar-display:foobar-display-container:jib
