./gradlew :order-service:order-container:clean
./gradlew :order-service:order-container:jibDockerBuild
./gradlew :order-service:order-container:jib

docker build --tag chagchagchag/order-container:0.0.1 .
docker push chagchagchag/order-container:0.0.1

docker build --tag chagchagchag/order-container .
docker push chagchagchag/order-container