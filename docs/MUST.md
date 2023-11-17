### 2023.10.02
현재 주문,사용자,카탈로그 생성시 Id 생성은 DB 저장 직전에는 PrimaryKeyEntity 에서 생성한 Ulid 기반 UUID를 기반으로 하고 있다.
<br>

하지만, Dto 의 구조를 아직 확실하게 결정짓지 못했다. 그래서 OrderDto, UserDto, CatalogDto 에 id, orderId, catalogId 등과 같은 항목들을 create 요청시 WAS 레벨에서 난수 생성을 임의로 해두고 있다. (DB 저장시에는 다른 UUID를 다시 생성해서 넣는다)
<br>

이 부분에 대해서는 SAGA, Outbox, Kafka 등을 도입을 모두 마무리 지은 후에 결정해 볼 생각 중 이다.
<br>
