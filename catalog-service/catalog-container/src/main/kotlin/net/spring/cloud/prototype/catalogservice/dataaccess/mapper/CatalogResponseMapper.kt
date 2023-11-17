package net.spring.cloud.prototype.catalogservice.dataaccess.mapper

import net.spring.cloud.prototype.catalogservice.application.valueobject.ReadCatalogResponse
import net.spring.cloud.prototype.catalogservice.dataaccess.entity.CatalogEntity
import org.springframework.stereotype.Component

@Component
class CatalogResponseMapper {

    fun fromCatalogEntity(catalogEntity: CatalogEntity)
    : ReadCatalogResponse {
        return ReadCatalogResponse(
            productId = catalogEntity.productId,
            productName = catalogEntity.productName,
            unitPrice = catalogEntity.unitPrice,
            stock = catalogEntity.stock,
            createdAt = catalogEntity.createdAt
        )
    }

}