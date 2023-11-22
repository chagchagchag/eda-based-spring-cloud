cd kafka
source shutdown-kafka-compose.sh
cd ..

cd mysql
docker-compose down
cd ..

cd web-service
docker-compose down
cd ..
