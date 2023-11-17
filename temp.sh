./gradlew :spring-cloud:api-gateway:clean
./gradlew :spring-cloud:api-gateway:build

cd spring-cloud/api-gateway
docker build --tag soongoood/api-gateway:0.0.1 .
docker push soongoood/api-gateway:0.0.1

docker build --tag soongoood/api-gateway .
docker push soongoood/api-gateway

cd ../../
cd docker-compose
docker-compose -f common.yml -f docker-compose.yml up -d
