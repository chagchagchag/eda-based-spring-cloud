package net.spring.cloud.prototype.domain.event

import java.math.BigInteger
import java.time.LocalDate
import java.util.*

data class OrderCreatedEvent (
    val orderId: UUID,
    val userId: UUID,
    val productId: UUID,
    val qty: BigInteger,
    val unitPrice: BigInteger,
    val totalPrice: BigInteger,
    val createdAt: LocalDate,
): OrderEvent(
    eventType = EventType.ORDER_CREATED
){
}