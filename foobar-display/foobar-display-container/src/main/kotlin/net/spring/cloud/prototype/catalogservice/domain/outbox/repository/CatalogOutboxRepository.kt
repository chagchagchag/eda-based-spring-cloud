package net.spring.cloud.prototype.catalogservice.domain.outbox.repository

import net.spring.cloud.prototype.catalogservice.domain.outbox.entity.CatalogOutboxEntity
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface CatalogOutboxRepository : JpaRepository<CatalogOutboxEntity, UUID> {

    @Query(
        "SELECT o " +
        "FROM CatalogOutboxEntity o " +
        "WHERE o.eventType = 'ORDER_CREATED' " +
        "AND o.sagaStatus = :sagaStatus " +
        "AND o.outboxStatus = :outboxStatus "
    )
    fun findAllCreatedOrderEvent(
        @Param("sagaStatus") sagaStatus : SagaStatus,
        @Param("outboxStatus") outboxStatus: OutboxStatus,
    ) : List<CatalogOutboxEntity>

}