package net.spring.cloud.prototype.orderservice.application.valueobject

import java.math.BigInteger
import java.time.OffsetDateTime

data class OrderCreatedResponse(
    val productId: String,
    val orderId: String,

    val qty: BigInteger,
    val unitPrice: BigInteger,
    val totalPrice: BigInteger,

    val createdAt: OffsetDateTime,
)
