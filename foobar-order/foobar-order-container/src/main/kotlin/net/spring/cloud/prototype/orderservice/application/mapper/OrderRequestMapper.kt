package net.spring.cloud.prototype.orderservice.application.mapper

import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.orderservice.application.valueobject.OrderRequest
import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@Component
class OrderRequestMapper {
    fun toCreateOrderCommand(orderRequest: OrderRequest, userId: String): CreateOrderCommand =
        CreateOrderCommand(
            orderId = UlidCreator.monotonicUuid(),
            userId = UUID.fromString(userId),
            productId = UUID.fromString(orderRequest.productId),
            qty = orderRequest.qty,
            unitPrice = orderRequest.unitPrice,
            totalPrice = orderRequest.unitPrice * orderRequest.qty,
            createdAt = OffsetDateTime.now(),
        )
}