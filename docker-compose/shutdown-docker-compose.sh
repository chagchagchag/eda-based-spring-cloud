cd kafka
source shutdown-kafka-compose.sh
cd ..

cd mysql
docker-compose down
cd ..

docker-compose down
