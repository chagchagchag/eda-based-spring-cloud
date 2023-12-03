package net.spring.cloud.prototype.orderservice.domain.outbox.repository

import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.orderservice.domain.outbox.entity.OrderOutboxEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface OrderOutboxRepository : JpaRepository<OrderOutboxEntity, UUID>{

    @Query(
        "SELECT o " +
        "FROM OrderOutboxEntity o " +
        "WHERE o.eventType = 'ORDER_CREATED' " +
            "AND o.outboxStatus = :outboxStatus " +
            "AND o.sagaStatus = :sagaStatus "
    )
    fun findAllCreatedOrderEvent(
        @Param("sagaStatus") sagaStatus: SagaStatus,
        @Param("outboxStatus") outboxStatus: OutboxStatus,
    ) : List<OrderOutboxEntity>
}