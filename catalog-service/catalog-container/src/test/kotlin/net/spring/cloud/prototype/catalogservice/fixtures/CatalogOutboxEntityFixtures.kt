package net.spring.cloud.prototype.catalogservice.fixtures

import net.spring.cloud.prototype.catalogservice.domain.outbox.entity.CatalogOutboxEntity
import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import java.time.ZonedDateTime

class CatalogOutboxEntityFixtures {

    companion object {

        fun fromNullOrderCreatedEvent()
        : CatalogOutboxEntity {
            return CatalogOutboxEntity(
                createdAt = ZonedDateTime.now(),
                sagaId = UlidCreator.monotonicUuid(),
                sagaStatus = SagaStatus.CREATED,
                outboxStatus = OutboxStatus.CREATED,
                eventType = EventType.ORDER_CREATED,
                payload = null,
            )
        }

        fun fromEmptyStringOrderCreatedEvent()
        : CatalogOutboxEntity {
            return CatalogOutboxEntity(
                createdAt = ZonedDateTime.now(),
                sagaId = UlidCreator.monotonicUuid(),
                sagaStatus = SagaStatus.CREATED,
                outboxStatus = OutboxStatus.CREATED,
                eventType = EventType.ORDER_CREATED,
                payload = "  ",
            )
        }

        fun randomNullPayloadEntityList5() : List<CatalogOutboxEntity> {
            return OrderCreatedEventFixtures
                .randomEventList5()
                .map { orderCreatedEvent ->  fromNullOrderCreatedEvent() }
        }

        fun randomEmptyStringPayloadEntityList5() : List<CatalogOutboxEntity> {
            return OrderCreatedEventFixtures
                .randomEventList5()
                .map { orderCreatedEvent -> fromEmptyStringOrderCreatedEvent() }
        }
    }

}