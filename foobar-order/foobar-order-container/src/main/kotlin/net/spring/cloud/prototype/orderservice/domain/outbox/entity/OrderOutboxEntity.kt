package net.spring.cloud.prototype.orderservice.domain.outbox.entity

import jakarta.persistence.*
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.dataaccess.entity.PrimaryKeyEntity
import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.domain.event.EventType
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "order_outbox")
class OrderOutboxEntity (
    @Column(name = "created_at")
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val sagaId: UUID = UlidCreator.monotonicUuid(),
    sagaStatus: SagaStatus,
    outboxStatus: OutboxStatus,
    eventType: EventType,
    payload: String?
): PrimaryKeyEntity(){

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var sagaStatus: SagaStatus = sagaStatus

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var outboxStatus: OutboxStatus = outboxStatus

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var eventType: EventType = eventType

    @Column(nullable = false, length = 2000)
    var payload: String? = payload

}