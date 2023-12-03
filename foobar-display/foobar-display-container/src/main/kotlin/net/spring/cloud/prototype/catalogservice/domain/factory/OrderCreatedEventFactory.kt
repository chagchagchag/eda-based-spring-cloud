package net.spring.cloud.prototype.catalogservice.domain.factory

import net.spring.cloud.prototype.catalogservice.domain.outbox.entity.CatalogOutboxEntity
import net.spring.cloud.prototype.catalogservice.kafka.ObjectMapperUtils
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import org.springframework.stereotype.Component

@Component
class OrderCreatedEventFactory (
    val objectMapperUtils: ObjectMapperUtils,
){

    fun fromEventString(eventString: String) : OrderCreatedEvent {
        return objectMapperUtils.fromEventString(eventString)
    }

    fun toOutboxEntity(event: OrderCreatedEvent) : CatalogOutboxEntity {
        return CatalogOutboxEntity(
            sagaId = event.sagaId,
            sagaStatus = SagaStatus.CREATED,
            outboxStatus = OutboxStatus.CREATED,
            eventType = event.eventType,
            payload = objectMapperUtils.serializeObject(event)
        )
    }

}