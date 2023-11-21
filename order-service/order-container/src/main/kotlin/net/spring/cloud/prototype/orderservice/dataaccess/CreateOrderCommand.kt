package net.spring.cloud.prototype.orderservice.dataaccess

import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

data class CreateOrderCommand(
    val orderId: UUID,
    val userId: UUID,
    val productId: UUID,
    val qty: BigInteger,
    val unitPrice: BigInteger,
    val totalPrice: BigInteger,
    val createdAt: OffsetDateTime,
)