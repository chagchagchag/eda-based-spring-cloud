package net.spring.cloud.prototype.orderservice.dataaccess.dto

import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

data class OrderDto(
    val orderId: UUID,
    val userId: UUID,
    val productId: UUID,
    val qty: BigInteger,
    val unitPrice: BigInteger,
    val totalPrice: BigInteger,
    val createdAt: OffsetDateTime,
)
