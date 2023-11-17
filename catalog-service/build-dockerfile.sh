./gradlew :catalog-service:catalog-container:clean
./gradlew :catalog-service:catalog-container:jibDockerBuild
./gradlew :catalog-service:catalog-container:jib

docker build --tag chagchagchag/catalog-service-container:0.0.1 .
docker push chagchagchag/catalog-service-container:0.0.1

docker build --tag chagchagchag/catalog-service-container .
docker push chagchagchag/catalog-service-container