package net.spring.cloud.prototype.orderservice.application.valueobject

import java.math.BigInteger
import java.time.LocalDate
import java.util.*

data class OrderResponse(
    val productId: UUID,
    val orderId: UUID,

    val qty: BigInteger,
    val unitPrice: BigInteger,
    val totalPrice: BigInteger,

    val createdAt: LocalDate,
)
