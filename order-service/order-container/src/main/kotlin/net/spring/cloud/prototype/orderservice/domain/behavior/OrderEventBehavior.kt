package net.spring.cloud.prototype.orderservice.domain.behavior

import net.spring.cloud.prototype.domain.event.OrderEvent
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto

interface OrderEventBehavior {
    fun createEvent(orderDto: OrderDto) : OrderEvent
}