package net.spring.cloud.prototype.orderservice.domain.behavior

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OrderEvent
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto

class OrderCreatedEventBehavior : OrderEventBehavior{
    override fun createEvent(orderDto: OrderDto): OrderEvent {
        return OrderCreatedEvent(
            orderId = orderDto.orderId,
            userId = orderDto.userId,
            productId = orderDto.productId,
            qty =
        )
    }
}