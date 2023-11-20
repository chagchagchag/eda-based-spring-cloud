package net.spring.cloud.prototype.catalogservice.domain.outbox

import net.spring.cloud.prototype.catalogservice.domain.outbox.entity.CatalogOutboxEntity
import net.spring.cloud.prototype.catalogservice.domain.outbox.repository.CatalogOutboxRepository
import net.spring.cloud.prototype.domain.event.CatalogStockStatus
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CatalogOutboxRepositoryHelper (
    val catalogOutboxRepository: CatalogOutboxRepository,
){

    @Transactional
    fun findAllCreatedOrderEvent(): List<CatalogOutboxEntity> {
        return catalogOutboxRepository.findAllCreatedOrderEvent(SagaStatus.CREATED, OutboxStatus.CREATED)
    }

    @Transactional
    fun updateToProcessing(catalogOutboxEntity: CatalogOutboxEntity)
    : CatalogOutboxEntity {
        catalogOutboxEntity.outboxStatus = OutboxStatus.PROCESSING
        catalogOutboxEntity.sagaStatus = SagaStatus.PROCESSING
        return catalogOutboxEntity
    }

    @Transactional
    fun updateByCatalogStockStatus(catalogStockStatus: CatalogStockStatus, catalogOutboxEntity: CatalogOutboxEntity){
        if(catalogStockStatus == CatalogStockStatus.NORMAL){
            catalogOutboxEntity.outboxStatus = OutboxStatus.FINISHED
            catalogOutboxEntity.sagaStatus = SagaStatus.FINISHED
        }
        else{
            catalogOutboxEntity.outboxStatus = OutboxStatus.PENDING
            catalogOutboxEntity.sagaStatus = SagaStatus.PENDING
        }
    }
}