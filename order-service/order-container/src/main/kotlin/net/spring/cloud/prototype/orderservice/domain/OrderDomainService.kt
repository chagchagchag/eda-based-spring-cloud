package net.spring.cloud.prototype.orderservice.domain

import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto

interface OrderDomainService {

    fun insertToOutbox (orderDto: OrderDto, eventType: EventType) : OrderCreatedEvent
}