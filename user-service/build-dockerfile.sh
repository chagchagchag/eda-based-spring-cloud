./gradlew :user-service:user-container:clean
./gradlew :user-service:user-container:jibDockerBuild
./gradlew :user-service:user-container:jib

docker build --tag chagchagchag/user-service-container:0.0.1 .
docker push chagchagchag/user-service-container:0.0.1

docker build --tag chagchagchag/user-service-container .
docker push chagchagchag/user-service-container