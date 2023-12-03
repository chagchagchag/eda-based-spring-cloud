package net.spring.cloud.prototype.catalogservice.domain.outbox.entity

import jakarta.persistence.*
import net.spring.cloud.prototype.catalogservice.domain.CatalogStockStatus
import net.spring.cloud.prototype.dataaccess.entity.PrimaryKeyEntity
import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "catalog_outbox")
class CatalogOutboxEntity (
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val sagaId: UUID = UlidCreator.monotonicUuid(),
    sagaStatus: SagaStatus,
    outboxStatus: OutboxStatus,
    eventType: EventType,
    payload: String?
): PrimaryKeyEntity(){

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var sagaStatus: SagaStatus = sagaStatus

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var outboxStatus: OutboxStatus = outboxStatus

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var eventType: EventType = eventType

    @Column(nullable = false, length = 3000)
    var payload: String? = payload

    fun updateToProcessing(){
        outboxStatus = OutboxStatus.PROCESSING
        sagaStatus = SagaStatus.PROCESSING
    }

    fun validatePayload(){
        if(payload == null || payload!!.isBlank())
            throw IllegalArgumentException("SagaID 에 대해 outboxEntity 가 비어있습니다.")
    }

    fun postHandleOutbox(catalogStockStatus: CatalogStockStatus){
        if(catalogStockStatus == CatalogStockStatus.NORMAL)
            outboxStatus = OutboxStatus.FINISHED
        else
            outboxStatus = OutboxStatus.PENDING
    }

    fun postHandleSaga(catalogStockStatus: CatalogStockStatus){
        if(catalogStockStatus == CatalogStockStatus.NORMAL)
            sagaStatus = SagaStatus.FINISHED
        else
            sagaStatus = SagaStatus.PENDING
    }


}