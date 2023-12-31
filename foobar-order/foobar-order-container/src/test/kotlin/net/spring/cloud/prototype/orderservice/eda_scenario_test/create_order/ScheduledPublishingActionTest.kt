package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.orderservice.config.properties.OrderServiceProperties
import net.spring.cloud.prototype.orderservice.domain.factory.OrderCreatedEventFactory
import net.spring.cloud.prototype.orderservice.domain.outbox.OrderOutboxHelper
import net.spring.cloud.prototype.orderservice.domain.outbox.repository.OrderOutboxRepository
import net.spring.cloud.prototype.orderservice.domain.publisher.OrderCreatedEventPublisher
import net.spring.cloud.prototype.orderservice.fixtures.OrderOutboxEntityFixtures
import net.spring.cloud.prototype.orderservice.kafka.KafkaStringProducer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.springframework.kafka.support.SendResult
import java.util.concurrent.CompletableFuture

@ExtendWith(MockitoExtension::class)
class ScheduledPublishingActionTest {

    /**
     * OrderOutboxScheduler 는 주기적으로 OrderOutboxHelper 를 통해 publishOrderCreatedEvent() 를 호출한다.
     * - (SQL 검증) OrderOutboxRepository 의 findAllCreatedOrderEvent() 쿼리 검증
     *   : domain/outbox/repository/OrderOutboxRepositoryTest 에 작성 완료
     * - (유효성 체크 검증) OrderOutboxEntity 의 payload 가 null 일때 IllegalArgumentException
     * - (유효성 체크 검증) OrderOutboxEntity 의 payload 가 공백문자일 때 IllegalArgumentException
     * - (전송 구문 호출 여부 검증) OrderCreatedEventPublisher 로 Kafka 메시지 큐에 데이터 전송 여부 검증
     */

    @Mock
    private lateinit var orderOutboxRepository: OrderOutboxRepository

    @Mock
    private lateinit var orderCreatedEventPublisher: OrderCreatedEventPublisher

    @Mock
    private lateinit var orderCreatedEventFactory: OrderCreatedEventFactory

    @Mock
    private lateinit var mockFuture: CompletableFuture<SendResult<String, String>>

    @Mock
    private lateinit var orderServiceProperties: OrderServiceProperties

    @Mock
    private lateinit var kafkaStringProducer: KafkaStringProducer

    @InjectMocks
    private lateinit var orderOutboxHelper: OrderOutboxHelper

    // 추후 에러코드 정형화 예정
    val EMPTY_PAYLOAD_EVENT_STRING_EXCEPTION_MESSAGE = "SagaID 에 대해 outboxEntity 가 비어있습니다."
    
    @Test
    @DisplayName("(유효성 체크 검증) OrderOutboxEntity 의 payload 가 null 일때 IllegalArgumentException")
    fun `(유효성 체크 검증) OrderOutboxEntity 의 payload 가 null 일때 IllegalArgumentException`(){
        // given
        val nullPayloadEntityList = OrderOutboxEntityFixtures
            .randomNullPayloadEntityList5()

        // when
        Mockito
            .`when`(orderOutboxRepository.findAllCreatedOrderEvent(SagaStatus.CREATED, OutboxStatus.CREATED))
            .thenReturn(nullPayloadEntityList)

        // then
        Assertions
            .assertThatThrownBy { orderOutboxHelper.publishOrderCreatedEvent() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(EMPTY_PAYLOAD_EVENT_STRING_EXCEPTION_MESSAGE)
    }

    @Test
    @DisplayName("(유효성 체크 검증) OrderOutboxEntity 의 payload 가 공백문자일 때 IllegalArgumentException")
    fun `(유효성 체크 검증) OrderOutboxEntity 의 payload 가 공백문자일 때 IllegalArgumentException`(){
        // given
        val emptyPayloadEntityList = OrderOutboxEntityFixtures
            .randomEmptyStringPayloadEntityList5()
        
        // when
        Mockito
            .`when`(orderOutboxRepository.findAllCreatedOrderEvent(SagaStatus.CREATED, OutboxStatus.CREATED))
            .thenReturn(emptyPayloadEntityList)
        
        // then
        Assertions
            .assertThatThrownBy { orderOutboxHelper.publishOrderCreatedEvent() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(EMPTY_PAYLOAD_EVENT_STRING_EXCEPTION_MESSAGE)

    }

    @Test
    @DisplayName("(전송 구문 호출 여부 검증) OrderCreatedEventPublisher 로 Kafka 메시지 큐에 데이터 전송 여부 검증")
    fun `(전송 구문 호출 여부 검증) OrderCreatedEventPublisher 로 Kafka 메시지 큐에 데이터 전송 여부 검증`(){
        // given
        val entityList = OrderOutboxEntityFixtures
            .randomCreatedEntityList5()

        // when
        Mockito
            .`when`(orderOutboxRepository.findAllCreatedOrderEvent(SagaStatus.CREATED, OutboxStatus.CREATED))
            .thenReturn(entityList)

        Mockito
            .`when`(orderCreatedEventPublisher.sendEvent(any(), any()))
            .thenReturn(mockFuture)

        // then
        orderOutboxHelper.publishOrderCreatedEvent()

        Mockito
            .verify(orderCreatedEventPublisher, times(5))
            .sendEvent(any(), any())
    }

}