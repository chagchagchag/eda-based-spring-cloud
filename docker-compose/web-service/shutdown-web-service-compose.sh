rm -rf volumes
docker-compose -f common.yml -f docker-compose.yml down

cnt_foobar_network=`docker network ls --filter name=foobar-network | wc -l`
cnt_foobar_network=$(($cnt_foobar_network -1))

if [ $cnt_foobar_network -ne 0 ]
then
  echo "'foobar-network' 가 존재합니다. 'foobar-network' 를 삭제합니다."
  docker network rm foobar-network
fi