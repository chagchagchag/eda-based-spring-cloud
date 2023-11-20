package net.spring.cloud.prototype.catalogservice.domain

import net.spring.cloud.prototype.catalogservice.dataaccess.CatalogDataAccessHelper
import net.spring.cloud.prototype.catalogservice.domain.outbox.CatalogOutboxRepositoryHelper
import net.spring.cloud.prototype.catalogservice.kafka.ObjectMapperUtils
import net.spring.cloud.prototype.domain.converter.EventConverter
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Component
class CatalogOutboxHelper (
    val objectMapperUtils: ObjectMapperUtils,
    val catalogOutboxRepositoryHelper: CatalogOutboxRepositoryHelper,
    val catalogDataAccessHelper: CatalogDataAccessHelper,
){

    @Transactional
    fun handleOrderCreatedEvent(){
        catalogOutboxRepositoryHelper
            .findAllCreatedOrderEvent()
            .forEach { catalogOutboxEntity ->
                catalogOutboxEntity.validatePayload()
                catalogOutboxEntity.updateToProcessing()

                val orderCreatedEvent = EventConverter
                    .fromEventString<OrderCreatedEvent>(objectMapperUtils.nullableObjectMapper, catalogOutboxEntity.payload!!)

                val catalogStockStatus = catalogDataAccessHelper
                    .decreaseCatalogStock(orderCreatedEvent.productId, orderCreatedEvent.qty)

                catalogOutboxRepositoryHelper
                    .updateByCatalogStockStatus(catalogStockStatus, catalogOutboxEntity)
            }
    }

}