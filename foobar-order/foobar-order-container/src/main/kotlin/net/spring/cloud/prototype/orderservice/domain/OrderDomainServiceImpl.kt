package net.spring.cloud.prototype.orderservice.domain

import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import net.spring.cloud.prototype.orderservice.domain.outbox.OrderOutboxHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderDomainServiceImpl (
    val orderOutboxHelper: OrderOutboxHelper,
): OrderDomainService {

    private val logger : Logger = LoggerFactory.getLogger(javaClass)

    override fun insertToOutbox(orderDto: OrderDto, eventType: EventType): OrderCreatedEvent {

        // 1) OrderCreatedEvent 를 생성한다.
        //  OrderCreatedEvent 생성 시에는 EventCreateBehavior 를 활용한다.
        // 2) OrderOutboxEntity 에 OrderCreatedEvent 를 담아서 기록한다.
        return orderOutboxHelper.insertToOutbox(orderDto, eventType)
    }
}