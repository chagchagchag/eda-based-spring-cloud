package net.spring.cloud.prototype.orderservice.dataaccess

import java.math.BigInteger
import java.time.LocalDate
import java.util.UUID

data class CreateOrderCommand(
    val orderId: UUID,
    val userId: UUID,
    val productId: UUID,
    val qty: BigInteger,
    val unitPrice: BigInteger,
    val totalPrice: BigInteger,
    val createdAt: LocalDate,
)