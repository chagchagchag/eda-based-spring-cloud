package net.spring.cloud.prototype.catalogservice.application

import net.spring.cloud.prototype.catalogservice.application.valueobject.ReadCatalogResponse
import java.util.*

interface CatalogApplicationService {
    fun findOneProduct(productId: UUID) : ReadCatalogResponse
}