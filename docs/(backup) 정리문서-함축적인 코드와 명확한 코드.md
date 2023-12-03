## 함축적인 코드와 명확한 코드

### 함축적인 코드

OrderCreatedEvent 가 발생했을 때 조회 서비스에는 OrderCreatedEvent 에 대한 productId가 존재하지 않는 경우가 있다고 해보자. 이렇게 제품코드인 productId 가 조회 서비스내의 DB에 존재하지 않을 경우는 CatalogOutboxEntity 의 sagaStatus 는 SagaStatus.PENDING, outboxStatus 는 OutboxStatus.PENDING 으로 세팅해줘야 한다. 

만약 조회 서비스 내의 DB 내에 OrderCreatedEvent 에 대한 제품코드인 productId가 존재한다면  이때는 수량을 차감하고 SagaStatus 는 SagaStatus.FINISHED, OutboxStatus 는 OutboxStatus.FINISHED 로 세팅한다.

지금까지 이야기한 코드는 아래와 같다.

```kotlin
@Component
class CatalogOutboxHelper (
  //...
){
  
  @Transactional
  fun handleOrderCreatedEvent(){

    // ...
    
    val orderCreatedEvent = EventConverter.fromEventString<OrderCreatedEvent>(objectMapperUtils.nullableObjectMapper, outboxEntity.payload!!)

    catalogJpaRepository
    .findByProductId(orderCreatedEvent.productId)
    ?.let{ catalogEntity ->
          catalogEntity.decreaseQty(orderCreatedEvent.qty)
          catalogJpaRepository.save(catalogEntity)

          outboxEntity.outboxStatus = OutboxStatus.FINISHED
          outboxEntity.sagaStatus = SagaStatus.FINISHED
         }
    ?: run{
      outboxEntity.outboxStatus = OutboxStatus.PENDING
      outboxEntity.sagaStatus = SagaStatus.PENDING
    }
  }
  
  // ...
  
}
```



코드가 함축적이고 여러 기능들이 하나의 함수 안에서 실행되고 있다. 하나의 기능의 수정이 발생시 여러 가지 기능들이 수정될 수도 있는 Side Effect 발생 가능성 역시 있다.



### 명확한 코드로 분류

위와 같은 코드를 몇개의 메서드로 분류해보자.

먼저, enum 을 통해 아래의 결과 코드들을 분류했다.

```kotlin
enum class CatalogStockStatus {
    NORMAL, ORDERED_NOT_EXISTING_CATALOG,
}
```



이제 상태 코드에 대해 어떤 행동을 기술할지 아래와 같이 문장으로 정리해보면 아래와 같다.

각각의 스텝에서는 어떤 인자값인지만 전달받아서 각각의 메서드는 자기 역할만 수행한다. 각각의 메서드가 외부 상황을 알 필요가 없게끔 절차를 문장으로 작성해보면 아래와 같다.

1\.진열품목 조회한다. 그리고 진열품목의 존재 여부에 따라 아래의 결과값을 리턴한다.

- Catalog가 존재한다면 return CatalogStockStatus.NORMAL
- Catalog가 존재하지 않는다면 return CatalogStockStatus.ORDERED_NOT_EXISTING_CATALOG

2\. 진열품목의 수량을 Update 한다. 이때 1\. 에서 얻은 CatalogStockStatus를 인자값으로 전달한다..

- CatalogStockStatus.NORMAL 가 인자값으로  전달된 경우에만 CatalogEntity 의 재고를 차감해서 업데이트 한다.

3\. Outbox에 처리 결과를 기록한다.

- CatalogStockStatus.NORMAL 일 경우 CatalogOutboxEntity의 sagaStatus = FINISHED, outboxStatus = FINISHED 로 기록한다.
- CatalogStockStatus.ORDERED\_NOT\_EXISTING\_CATALOG 일 경우 CatalogOutboxEntity 의 sagaStatus = PENDING, outboxStatus = PENDING 으로 기록한다.







