package net.spring.cloud.prototype.catalogservice.dataaccess

import net.spring.cloud.prototype.catalogservice.dataaccess.repository.CatalogJpaRepository
import net.spring.cloud.prototype.catalogservice.domain.CatalogStockStatus
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CatalogDataAccessHelper (
    val catalogJpaRepository: CatalogJpaRepository,
){

    @Transactional
    fun updateOrderCreatedItem(orderCreatedEvent: OrderCreatedEvent): CatalogStockStatus {
        val productId = orderCreatedEvent.productId
        val qty = orderCreatedEvent.qty

        return catalogJpaRepository
            .findByProductId(productId)
            ?.let{ catalogEntity ->
                catalogEntity.decreaseQty(qty)
                CatalogStockStatus.NORMAL
            }
            ?: CatalogStockStatus.ORDERED_NOT_EXISTING_CATALOG
    }

}