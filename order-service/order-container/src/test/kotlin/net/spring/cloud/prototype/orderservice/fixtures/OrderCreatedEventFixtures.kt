package net.spring.cloud.prototype.orderservice.fixtures

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto

class OrderCreatedEventFixtures {
    companion object{
        fun fromOrderDto(orderDto : OrderDto) : OrderCreatedEvent {
            return OrderCreatedEvent(
                orderId = orderDto.orderId,
                userId = orderDto.userId,
                productId = orderDto.productId,
                qty = orderDto.qty,
                unitPrice = orderDto.unitPrice,
                totalPrice = orderDto.totalPrice,
                createdAt = orderDto.createdAt,
            )
        }
    }
}