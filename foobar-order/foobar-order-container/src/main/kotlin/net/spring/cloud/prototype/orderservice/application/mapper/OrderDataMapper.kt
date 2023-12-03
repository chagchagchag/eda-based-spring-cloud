package net.spring.cloud.prototype.orderservice.application.mapper

import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import net.spring.cloud.prototype.orderservice.application.valueobject.ResponseOrder
import net.spring.cloud.prototype.orderservice.dataaccess.entity.OrderEntity
import org.springframework.stereotype.Component

@Component
class OrderDataMapper {
    fun ofOrderResponseList(orderList: Iterable<OrderEntity>): List<ResponseOrder> {
        val list = mutableListOf<ResponseOrder>()

        orderList.forEach { list.add(ofOrderResponse(it)) }

        return list.toList()
    }

    fun ofOrderResponse(orderEntity: OrderEntity): ResponseOrder = ResponseOrder(
        orderId = orderEntity.id.toString(),
        productId = orderEntity.productId.toString(),
        qty = orderEntity.qty,
        unitPrice = orderEntity.unitPrice,
        totalPrice = orderEntity.totalPrice,
        createdAt = orderEntity.createdAt,
    )

    fun toOrderEntity(orderDto: OrderDto) : OrderEntity = OrderEntity(
        userId = orderDto.userId,
        productId = orderDto.productId,
        qty = orderDto.qty,
        totalPrice = orderDto.totalPrice,
        unitPrice = orderDto.unitPrice,
        createdAt = orderDto.createdAt,
    )
}