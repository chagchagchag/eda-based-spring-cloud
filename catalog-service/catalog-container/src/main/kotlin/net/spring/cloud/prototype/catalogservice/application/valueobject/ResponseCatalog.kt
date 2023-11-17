package net.spring.cloud.prototype.catalogservice.application.valueobject

import java.time.LocalDate

data class ResponseCatalog(
    val productId: String,
    val productName: String,
    val unitPrice: Int,
    val stock: Int,
    val createdAt: LocalDate,
)
