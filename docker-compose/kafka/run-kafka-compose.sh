cnt_foobar_network=`docker network ls --filter name=foobar-network | wc -l`
cnt_foobar_network=$(($cnt_foobar_network -1))

if [ $cnt_foobar_network -eq 0 ]
then
  echo "'foobar-network' 가 존재하지 않습니다. 'foobar-network'를 새로 생성합니다."
  docker network create foobar-network
fi

docker-compose -f common.yml -f zookeeper.yml up -d
docker-compose -f common.yml -f kafka_cluster.yml up -d
#docker-compose -f common.yml -f kafka_connect.yml up -d
