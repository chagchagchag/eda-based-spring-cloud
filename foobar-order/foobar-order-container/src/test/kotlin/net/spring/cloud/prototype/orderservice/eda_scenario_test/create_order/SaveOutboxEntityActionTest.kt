package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.orderservice.domain.factory.OrderCreatedEventFactory
import net.spring.cloud.prototype.orderservice.domain.outbox.OrderOutboxHelper
import net.spring.cloud.prototype.orderservice.domain.outbox.repository.OrderOutboxRepository
import net.spring.cloud.prototype.orderservice.domain.publisher.OrderCreatedEventPublisher
import net.spring.cloud.prototype.orderservice.fixtures.OrderCreatedEventFixtures
import net.spring.cloud.prototype.orderservice.fixtures.OrderDtoFixtures
import net.spring.cloud.prototype.orderservice.fixtures.OrderOutboxEntityFixtures
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any

@ExtendWith(MockitoExtension::class)
class SaveOutboxEntityActionTest {

    @Mock
    private lateinit var orderOutboxRepository: OrderOutboxRepository

    @Mock
    private lateinit var orderCreatedEventPublisher: OrderCreatedEventPublisher

    @Mock
    private lateinit var orderCreatedEventFactory: OrderCreatedEventFactory

    @InjectMocks
    private lateinit var orderOutboxHelper: OrderOutboxHelper

    @Test
    @DisplayName("OrderOutboxRepository::save() 메서드 정상호출 확인")
    fun `(OrderOutboxHelper) insertToOutbox 메서드 검증 - OrderOutboxEntity 저장`(){
        val orderDto = OrderDtoFixtures.whenOrderCreated()
        val eventType = EventType.ORDER_CREATED

        val orderCreatedEvent = OrderCreatedEventFixtures
            .fromOrderDto(orderDto)
        val orderOutboxEntity = OrderOutboxEntityFixtures
            .fromOrderCreatedEvent(orderCreatedEvent)

        Mockito
            .`when`(orderCreatedEventFactory.toOutboxEntity(any()))
            .thenReturn(orderOutboxEntity)

        Mockito
            .`when`(orderCreatedEventFactory.fromOrderDto(any()))
            .thenReturn(orderCreatedEvent)

        orderOutboxHelper.insertToOutbox(orderDto, eventType)

        Mockito
            .verify(orderOutboxRepository, Mockito.times(1))
            .save(any())
    }

}