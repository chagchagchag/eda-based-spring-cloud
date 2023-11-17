package net.spring.cloud.prototype.catalogservice.application

import net.spring.cloud.prototype.catalogservice.application.valueobject.ReadCatalogResponse
import net.spring.cloud.prototype.catalogservice.dataaccess.mapper.CatalogResponseMapper
import net.spring.cloud.prototype.catalogservice.dataaccess.repository.CatalogJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class CatalogSagaHelper (
    val catalogJpaRepository: CatalogJpaRepository,
    val catalogResponseMapper: CatalogResponseMapper,
){
    fun findOneProduct(productId: UUID): ReadCatalogResponse {
        return catalogJpaRepository
            .findByProductId(productId)
            ?.let{catalogEntity ->
                catalogResponseMapper.fromCatalogEntity(catalogEntity)
            }
            ?: throw IllegalStateException("")
    }
}