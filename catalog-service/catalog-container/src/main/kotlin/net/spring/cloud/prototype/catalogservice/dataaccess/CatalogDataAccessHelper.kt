package net.spring.cloud.prototype.catalogservice.dataaccess

import net.spring.cloud.prototype.catalogservice.dataaccess.repository.CatalogJpaRepository
import net.spring.cloud.prototype.domain.event.CatalogStockStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.util.UUID

@Component
class CatalogDataAccessHelper (
    val catalogJpaRepository: CatalogJpaRepository,
){

    @Transactional
    fun decreaseCatalogStock(productId: UUID, qty: BigInteger) : CatalogStockStatus {
        return catalogJpaRepository
            .findByProductId(productId)
            ?.let { catalogEntity ->
                catalogEntity.decreaseQty(qty)
                CatalogStockStatus.NORMAL
            }
            ?: CatalogStockStatus.ORDERED_NOT_EXISTING_CATALOG
    }

}