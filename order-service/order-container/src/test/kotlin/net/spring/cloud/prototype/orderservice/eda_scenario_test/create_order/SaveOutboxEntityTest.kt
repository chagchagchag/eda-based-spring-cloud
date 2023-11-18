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
import org.springframework.boot.test.mock.mockito.MockBean

@ExtendWith(MockitoExtension::class)
class SaveOutboxEntityTest {

    @Mock
    private lateinit var orderOutboxRepository: OrderOutboxRepository

    @Mock
    private lateinit var orderCreatedEventPublisher: OrderCreatedEventPublisher

    @Mock
    private lateinit var orderCreatedEventFactory: OrderCreatedEventFactory

    @InjectMocks
    private lateinit var orderOutboxHelper: OrderOutboxHelper

    @Test
    @DisplayName("OrderOutboxRepository::save() 메서드 정상호출 ?")
    fun `OrderOutboxRepository 를 통해 save() 메서드 호출을 정상적으로 하는지 검증`(){
        val orderDto = OrderDtoFixtures.whenOrderCreated()
        val eventType = EventType.ORDER_CREATED
        val orderCreatedEvent = OrderCreatedEventFixtures.fromOrderDto(orderDto)
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

    @Test
    fun `OrderCreatedEvent 객체에 대해서 OrderOutboxRepository 에 OrderOutboxEntity 가 저장되는지 검증한다`(){

    }

}