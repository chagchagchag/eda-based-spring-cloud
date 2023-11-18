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

    /**
     * OrderDomainService 의 동작을 검증한다.
     * OrderCommandHandler 는 OrderCreateHelper (dataaccess) 의 createOrder 를 호출하는데,
     * OrderCreateHelper 는 createOrder(command) 호출에 대해 OrderEntity 저장 + OrderCreatedEvent 생성 & Outbox 에 저장 을 수행한다.
     *  OrderCreatedEvent 생성 & Outbox 에 저장 :
     *      (domain) saveOrderCreatedEventAtOutbox(command) 메서드가 그 역할을 수행한다.
     *
     *  OrderCreatedEvent 객체에 대해서 OrderOutboxRepository 에 OrderOutboxEntity 가 저장되는지 검증한다.
     *
     *
     *  참고)
     *  이 외의 유효성 검증이 필요한데, 아직까지는 기획 요건이 확립되지 않아서 유효성 검증 기능과 별도의 컴포넌트 구성은 생략해두었고,
     *  추후 추가 개발 계획이 필요하면 해당 기능 추가와 테스트 코드 추가 예정.
     */

    @Test
    fun abc(){

    }


    // OrderOutboxHelper 의 insertToOutbox() 는 OrderCreatedEvent 객체 생성을 하고, OutboxRepository 에 OrderCreatedEvent 를 기록한다
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