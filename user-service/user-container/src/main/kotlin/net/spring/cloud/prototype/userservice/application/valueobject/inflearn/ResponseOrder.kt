package net.spring.cloud.prototype.userservice.application.valueobject.inflearn

import java.time.LocalDate

data class ResponseOrder(
    val productId: String,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val createdAt: LocalDate,
    val orderId: String,
)