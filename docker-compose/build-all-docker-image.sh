cd ..
./gradlew :spring-cloud:api-gateway:clean
./gradlew :spring-cloud:api-gateway:build

./gradlew :spring-cloud:config-service:clean
./gradlew :spring-cloud:config-service:build

./gradlew :spring-cloud:discovery-service:clean
./gradlew :spring-cloud:discovery-service:build

./gradlew :user-service:user-container:clean
./gradlew :user-service:user-container:build

./gradlew :order-service:order-container:clean
./gradlew :order-service:order-container:build

./gradlew :catalog-service:catalog-container:clean
./gradlew :catalog-service:catalog-container:build

cd spring-cloud/api-gateway
docker build --tag soongoood/api-gateway:0.0.1 .
docker push soongoood/api-gateway:0.0.1

docker build --tag soongoood/api-gateway .
docker push soongoood/api-gateway
cd ../..

cd spring-cloud/config-service
docker build --tag soongoood/config-service:0.0.1 .
docker push soongoood/config-service:0.0.1

docker build --tag soongoood/config-service .
docker push soongoood/config-service
cd ../..

cd spring-cloud/discovery-service
docker build --tag soongoood/discovery-service:0.0.1 .
docker push soongoood/discovery-service:0.0.1

docker build --tag soongoood/discovery-service .
docker push soongoood/discovery-service
cd ../..

cd user-service
docker build --tag soongoood/user-container:0.0.1 .
docker push soongoood/user-container:0.0.1

docker build --tag soongoood/user-container .
docker push soongoood/user-container
cd ..

cd order-service
docker build --tag soongoood/order-container:0.0.1 .
docker push soongoood/order-container:0.0.1

docker build --tag soongoood/order-container .
docker push soongoood/order-container
cd ..

cd catalog-service
docker build --tag soongoood/catalog-container:0.0.1 .
docker push soongoood/catalog-container:0.0.1

docker build --tag soongoood/catalog-container .
docker push soongoood/catalog-container
cd ..
cd docker-compose