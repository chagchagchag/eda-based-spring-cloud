package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import org.junit.jupiter.api.Test

class ScheduleSendingSagaMessageTest {

    /**
     * OrderOutboxScheduler 의 동작을 검증한다.
     *  - Scheduler 를 통해서 주기적으로 'order_outbox' 테이블에서 OrderCreatedEvent 를 잘 읽어들이는지 검증한다.
     *  - 'order_outbox' 테이블에서 읽어들인 OrderOutboxEntity 를 OrderCreatedEvent 객체로 잘 변환하는지 검증한다.
     *  - 변환된 OrderCreatedEvent 객체를 OrderCreatedEventPublisher 의 sendEvent 메서드가 잘 호출되는지 검증한다.
     */

    @Test
    fun `Scheduler 를 통해서 'order_outbox' 에서 OrderCreatedEvent 객체를 읽어들인 후 Kafka 로의 메시지 전송을 잘 호출하는지 검증한다`(){

    }
}