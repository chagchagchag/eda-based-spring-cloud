package net.spring.cloud.prototype.catalogservice.application.valueobject

import java.math.BigInteger
import java.time.ZonedDateTime
import java.util.UUID

data class ReadCatalogResponse(
    val productId: UUID,
    val productName: String,
    val unitPrice: BigInteger,
    val stock: BigInteger,
    val createdAt: ZonedDateTime
)
