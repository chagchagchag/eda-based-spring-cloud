package net.spring.cloud.prototype.orderservice.application.valueobject

import java.math.BigInteger

data class RequestOrder(
    val productId: String,
    val qty: BigInteger,
    val unitPrice: BigInteger
)