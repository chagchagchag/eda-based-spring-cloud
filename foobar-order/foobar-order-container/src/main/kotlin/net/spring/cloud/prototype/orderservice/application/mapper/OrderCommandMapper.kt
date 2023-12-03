package net.spring.cloud.prototype.orderservice.application.mapper

import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand
import org.springframework.stereotype.Component

@Component
class OrderCommandMapper {
    fun toOrderDto(createOrderCommand: CreateOrderCommand) : OrderDto {
        return OrderDto(
            orderId = createOrderCommand.orderId,
            userId = createOrderCommand.userId,
            productId = createOrderCommand.productId,
            qty = createOrderCommand.qty,
            unitPrice = createOrderCommand.unitPrice,
            totalPrice = createOrderCommand.totalPrice,
            createdAt = createOrderCommand.createdAt,
        )
    }
}