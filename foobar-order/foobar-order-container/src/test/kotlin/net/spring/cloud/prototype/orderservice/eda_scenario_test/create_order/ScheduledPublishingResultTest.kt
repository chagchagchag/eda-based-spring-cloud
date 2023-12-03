package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ScheduledPublishingResultTest {
    /**
     * OrderCreatedEvent 생성 후 아래의 내용들이 잘 수행되는지를 기능이 아닌 '결과값'을 검증한다.
     * - OrderCreatedEvent 를 OrderCreatedEventPublisher 가 잘 전송하는지를 메시지 큐에서 ConsumerRecord 를 poll() 통해 조회후 확인한다.
     * - OutboxRepository 에 outbox 엔티티를 잘 추가되었는지 결과 검증
     *
     * 테스트 메서드
     * - (전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증
     * - (전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증
     */

    @Test
    @DisplayName("(전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증")
    fun `(전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증`(){

    }

    @Test
    @DisplayName("(전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증")
    fun `(전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증`(){

    }

}