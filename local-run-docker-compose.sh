cd docker-compose
source run-docker-compose.sh
cd ..

#cd docker-compose
#docker-compose -f common.yml -f docker-compose.yml up -d

# kafka 는 MSA + 카프카 커넥트 기능 완료된 후 활성화하기로 함.
# 아래 정의한 카프카 구동 스크립트는 오류 없이 잘 실행됩니다.
# 현재 카프카 커넥트 추가중이어서 MSA들간의 연동작업을 멈춰둔 상태입니다
#cd kafka
#docker-compose -f common.yml -f zookeeper.yml up -d
#docker-compose -f common.yml -f kafka_cluster.yml up -d