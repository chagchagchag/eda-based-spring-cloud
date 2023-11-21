package net.spring.cloud.prototype.orderservice.application.mapper

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.application.valueobject.OrderCreatedResponse
import net.spring.cloud.prototype.orderservice.application.valueobject.OrderResponse
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import org.springframework.stereotype.Component

@Component
class OrderResponseMapper {
    fun toOrderCreatedResponse(orderCreatedEvent: OrderCreatedEvent)
    : OrderCreatedResponse {
        return OrderCreatedResponse(
            productId = orderCreatedEvent.productId.toString(),
            orderId = orderCreatedEvent.orderId.toString(),
            qty = orderCreatedEvent.qty,
            unitPrice = orderCreatedEvent.unitPrice,
            totalPrice = orderCreatedEvent.totalPrice,
            createdAt = orderCreatedEvent.createdAt,
        )
    }

    fun toOrderResponse(orderDto: OrderDto): OrderResponse{
        return OrderResponse(
            productId = orderDto.productId,
            orderId = orderDto.orderId,
            qty = orderDto.qty,
            unitPrice = orderDto.unitPrice,
            totalPrice = orderDto.totalPrice,
            createdAt = orderDto.createdAt,
        )
    }
}