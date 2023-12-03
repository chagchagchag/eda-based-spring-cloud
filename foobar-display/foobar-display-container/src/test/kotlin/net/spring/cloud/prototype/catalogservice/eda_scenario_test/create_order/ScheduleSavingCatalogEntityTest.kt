package net.spring.cloud.prototype.catalogservice.eda_scenario_test.create_order

class ScheduleSavingCatalogEntityTest {
    /**
     * CatalogOutboxScheduler 를 통한 부수적인 함수 call 발생 시 마다
     * 아래의 동작 들을 수행시 에러가 발생하지 않는지, 호출이 이뤄지는지에 중점을 두어 검증한다.
     * 아래 프로세스 중 하나라도 실패하면 해당 기능의 변경이 있는지 검토 후 수정이 필요하다.
     *
     * - CatalogOutboxEntity 내의 paylod 를 OrderCreatedEvent 로 정성적으로 변환되어야 한다. 검증 역시 수행
     * - CatalogOutboxHelper 에서 OutboxEntity 를 OrderCreatedEvent로 잘 변환되어야 한다. 검증 역시 수행
     * - CatalogJpaRepository 가 주문한 제품의 Order 에 대한 productId 에 대한 수량이 잘 차감되는지 검증한다.
     */
}