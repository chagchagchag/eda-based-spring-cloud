package net.spring.cloud.prototype.orderservice.application.mapper

import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import net.spring.cloud.prototype.orderservice.dataaccess.entity.OrderEntity
import org.springframework.stereotype.Component

@Component
class OrderDataMapper {
    fun toOrderEntity(orderDto: OrderDto) : OrderEntity = OrderEntity(
        userId = orderDto.userId,
        productId = orderDto.productId,
        qty = orderDto.qty,
        totalPrice = orderDto.totalPrice,
        unitPrice = orderDto.unitPrice,
        createdAt = orderDto.createdAt,
    )
}