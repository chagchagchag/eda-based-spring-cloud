package net.spring.cloud.prototype.catalogservice.application.valueobject

import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

data class ReadCatalogResponse(
    val productId: UUID,
    val productName: String,
    val unitPrice: BigInteger,
    val stock: BigInteger,
    val createdAt: OffsetDateTime
)
