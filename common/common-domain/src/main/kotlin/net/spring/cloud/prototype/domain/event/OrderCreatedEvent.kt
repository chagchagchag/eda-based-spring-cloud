package net.spring.cloud.prototype.domain.event

import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

data class OrderCreatedEvent (
    val orderId: UUID,
    val userId: UUID,
    val productId: UUID,
    val qty: BigInteger,
    val unitPrice: BigInteger,
    val totalPrice: BigInteger,
    val createdAt: OffsetDateTime,
): OrderEvent(
    eventType = EventType.ORDER_CREATED
){
}