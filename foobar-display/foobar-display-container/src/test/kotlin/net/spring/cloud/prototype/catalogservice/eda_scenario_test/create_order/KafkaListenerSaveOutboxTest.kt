package net.spring.cloud.prototype.catalogservice.eda_scenario_test.create_order

class KafkaListenerSaveOutboxTest {
    /**
     * KafkaListener 가 수신한 메시지에 대해 아래 동작들을 수행시 에러가 발생하지 않는지, 호출이 이뤄지는지에 중점을 두어 검증한다.
     * 아래 프로세스 중 하나라도 실패하면 해당 기능의 변경이 있는지 검토 후 수정이 필요하다.
     *
     * - String 기반의 Kafka 메시지가 OrderCreatedEvent 로 변환되어야 한다.
     * - OrderCreatedEvent 객체로부터 CatalogOutboxEntity 객체가 잘 생성되어야 한다.
     * - OutboxRepository 에 CatalogOutboxEntity 가 잘 저장되는지 검증한다.
     */
}