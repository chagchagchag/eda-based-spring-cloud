package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

class CreateOrderScenarioTest {
    /**
     * OrderCreatedEvent 생성 후 아래의 내용들이 잘 수행되는지를 기능이 아닌 '결과값'을 검증한다.
     * - OutboxRepository 에 outbox 엔티티를 잘 추가되었는지 결과 검증
     * - OrderCreatedEvent 를 OrderCreatedEventPublisher 가 잘 전송하는지를 메시지 큐에서 ConsumerRecord 를 poll() 통해 조회후 확인한다.
     */
}