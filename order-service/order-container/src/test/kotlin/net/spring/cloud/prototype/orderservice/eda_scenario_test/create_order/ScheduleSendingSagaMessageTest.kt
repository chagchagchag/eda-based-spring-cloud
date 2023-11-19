package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ScheduleSendingSagaMessageTest {

    /**
     * OrderOutboxScheduler 는 주기적으로 OrderOutboxHelper 를 통해 publishOrderCreatedEvent() 를 호출한다.
     * - (SQL 검증) OrderOutboxRepository 의 findAllCreatedOrderEvent() 쿼리 검증
     *  - domain/outbox/repository/OrderOutboxRepositoryTest 에 작성 완료
     * - (유효성 체크 검증) OrderOutboxEntity 의 payload 가 null 일때 IllegalArgumentException
     * - (유효성 체크 검증) OrderOutboxEntity 의 payload 가 공백문자일 때 IllegalArgumentException
     * - (객체 변환 검증) OrderOutboxEntity 를 OrderCreatedEvent 객체로 잘 변환하는지 검증
     * - (전송 구문 호출 여부 검증) OrderCreatedEventPublisher 로 Kafka 메시지 큐에 데이터 전송 여부 검증
     * - (전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증
     * - (전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증
     */

    @Test
    @DisplayName("(유효성 체크 검증) OrderOutboxEntity 의 payload 가 null 일때 IllegalArgumentException")
    fun `(유효성 체크 검증) OrderOutboxEntity 의 payload 가 null 일때 IllegalArgumentException`(){

    }

    @Test
    @DisplayName("(유효성 체크 검증) OrderOutboxEntity 의 payload 가 공백문자일 때 IllegalArgumentException")
    fun `(유효성 체크 검증) OrderOutboxEntity 의 payload 가 공백문자일 때 IllegalArgumentException`(){

    }

    @Test
    @DisplayName("(객체 변환 검증) OrderOutboxEntity 를 OrderCreatedEvent 객체로 잘 변환하는지 검증")
    fun `(객체 변환 검증) OrderOutboxEntity 를 OrderCreatedEvent 객체로 잘 변환하는지 검증`(){

    }

    @Test
    @DisplayName("(전송 구문 호출 여부 검증) OrderCreatedEventPublisher 로 Kafka 메시지 큐에 데이터 전송 여부 검증")
    fun `(전송 구문 호출 여부 검증) OrderCreatedEventPublisher 로 Kafka 메시지 큐에 데이터 전송 여부 검증`(){

    }

    @Test
    @DisplayName("(전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증")
    fun `(전송 성공 후 처리 결과값 검증) OutboxStatus 는 FINISHED, SagaStatus 는 FINISHED 검증`(){

    }

    @Test
    @DisplayName("(전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증")
    fun `(전송 실패 후 처리 결과값 검증) OutboxStatus 는 PENDING, SagaStatus 는 PENDING 검증`(){

    }

}