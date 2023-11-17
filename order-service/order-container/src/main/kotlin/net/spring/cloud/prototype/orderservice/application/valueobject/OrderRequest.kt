package net.spring.cloud.prototype.orderservice.application.valueobject

import java.math.BigInteger

data class OrderRequest(
    val productId: String,
    val qty: BigInteger,
    val unitPrice: BigInteger,
)
