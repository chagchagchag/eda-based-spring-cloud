## 주문서비스와 조회서비스의 분리 구조 설계

## order-service ↔ 카프카 ↔ catalog-service 로의 이벤트 흐름

**order-service → 카프카**<br>

1. order-service 에서 주문을 생성한다. (POST /order)
2. OrderApplicationService 는 Helper 를 이용해 아래의 작업을 수행한다.
   1. OrderEntity 를 생성 후 OrderRepository 에 저장한다.
   2. OrderDomainService 로 saveOrderCreatedEventAtOutbox() 를 호출해서 outbox 에 저장한다.

3. scheduler 에서는 주기적으로 1초에 한번씩 아래의 작업을 수행한다.
   1. OrderOutboxRepository 에서 SagaStatus.CREATED, OutboxStatus.CREATED 인 데이터를 읽어들인다.
   2. 읽어들인 outboxEntity 객체 내의 String 타입의 payload 를 KafkaStringProducer 를 활용해서 메시지큐에 전송한다.


<br>

**카프카 → catalog-service**<br>

1. OrderCreatedEventListener 에서는 @KafkaListener 를 통해 `order-created-event` 토픽에 쌓인 데이터를 읽어들이고 아래의 작업을 수행한다.
   1. String 으로 직렬화되어 있는 이벤트 문자열을 ObjectMapper 를 이용해서 OrderCreatedEvent 객체로 역직렬화 한다.
   2. CatalogOutboxRepository 내에 payload 는 이벤트 문자열로, sagaId 는 OrderCreatedEvent 의 sagaId로, SagaStatus.CREATED, OutboxStatus.CREATED 로 세팅해서 CatalogOutboxRepository 에 CatalogOutboxEntity를 새로 Insert 해서 저장한다.

<br>

**catalog-service 스케쥴러 → CatalogRepository**<br>

1. 1초에 한번씩 CatalogOutboxRepository 를 조회해서 `ORDER_CREATED` 타입의 CatalogOutboxEntity를 읽어들여서 OrderCreatedEvent 객체를 생성한다.
2. 읽어들인 OrderCreatedEvent 내의 productId 를 기반으로 CatalogEntity 를 찾아서 조회한다.
3. 읽어들인 CatalogEntity 의 재고 수량(qty)을 차감한 후 저장한다. (Jpa 의 지연처리 활용)
4. CatalogOutboxEntity 의 상태를 OutboxStatus = FINISHED, SagaStatus = FINISHED 로 지정해서 CatalogRepository에 저장한다.

<br>



## 테스트 계획

- **카프카 전송/수신 기능 테스트**

- **order-service → 카프카**

- **카프카 → catalog-service**

- **catalog-service 스케쥴러 → CatalogRepository**

<br>



### 카프카 전송/수신 기능, Publisher 기능 테스트

주문서비스 (v)

- 카프카 내의 `order-created-event` 토픽에 Send 한 후 1초의 딜레이 이후 읽어들인 데이터가 전송한 데이터가 맞는지 확인
- 카프카 Send 시 원하는 토픽에 send 후 1초 지연 후 poll()

조회서비스

- 카프카 내의 `order-created-event` 토픽에 데이터가 들어있다고 가정한 후 1초의 딜레이 이후 읽어들인 데이터가 전송한 데이터가 맞는지 확인
- 카프카 Send 시 원하는 토픽에 send 후 1초 지연 후 poll()





### order-service → 카프카

#### OrderCreateHelper::createOrder(createOrderCommand)

- CreateOrderCommand 에 대해 OrderDomainService 의 insertToOutbox(orderDto, EventType) 을 정상적으로 호출하는지 확인한다.



#### OrderDomainService 

- insertToOutbox(orderDto, EventType)
  - orderOutboxHelper.insertToOutbox(orderDto, eventType) 을 정상적으로 호출하는지 검증



#### Scheduler → OrderOutboxHelper

- insertToOutbox(orderDto, eventType) (v)
  - orderOutboxRepository 에 orderOutboxEntity 를 save 하는지 검증

- publishOrderCreatedEvent()
  - (SQL 검증) OrderOutboxRepository 의 findAllCreatedOrderEvent() 
  - (유효성 체크 검증) OrderOutboxEntity 의 payload 가 null 일때 IllegalArgumentException
  - (유효성 체크 검증) OrderOutboxEntity 의 payload 가 공백문자일 때 IllegalArgumentException
  - (전송 구문 호출 여부 검증) OrderCreatedEventPublisher 로 Kafka 메시지큐에 데이터 전송여부 검증
  - (전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증
  - (전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증





#### OrderCreatedEventFactory

- fromCreateOrderCommand(command)
  - command 로부터 생성된 OrderCreatedEvent 의 orderId, userId, productId 가 누락되지 않고 제대로 생성되었는지 유효성 체크하는 테스트
- toOutboxEntity(event)
  - toOutboxEntity(event) 메서드로 생성된 OrderOutboxEntity 객체가 sagaStatus = CREATED, outboxStatus = CREATED, eventType = ORDER_CREATED 인지를 검증



### 카프카 → catalog-service







### catalog-service 스케쥴러 → CatalogRepository



