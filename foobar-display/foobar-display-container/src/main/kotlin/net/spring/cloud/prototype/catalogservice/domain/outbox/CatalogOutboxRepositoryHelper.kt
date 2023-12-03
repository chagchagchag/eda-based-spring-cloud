package net.spring.cloud.prototype.catalogservice.domain.outbox

import net.spring.cloud.prototype.catalogservice.domain.outbox.entity.CatalogOutboxEntity
import net.spring.cloud.prototype.catalogservice.domain.outbox.repository.CatalogOutboxRepository
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
    fun save(catalogOutboxEntity: CatalogOutboxEntity){
        catalogOutboxRepository.save(catalogOutboxEntity)
    }
}