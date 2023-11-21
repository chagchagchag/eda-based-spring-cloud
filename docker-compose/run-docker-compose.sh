cd kafka
source run-kafka-compose.sh
cd ..

cd mysql
docker-compose -f docker-compose.yml up -d
cd ..

cd web-service
docker-compose -f common.yml -f docker-compose.yml up -d
cd ..