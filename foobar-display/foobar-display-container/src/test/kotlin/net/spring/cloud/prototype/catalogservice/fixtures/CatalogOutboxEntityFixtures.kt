package net.spring.cloud.prototype.catalogservice.fixtures

import net.spring.cloud.prototype.catalogservice.domain.outbox.entity.CatalogOutboxEntity
import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.domain.fixtures.ObjectMapperFixtures
import java.time.OffsetDateTime

class CatalogOutboxEntityFixtures {

    companion object {
        fun fromOrderCreatedEvent(orderCreatedEvent: OrderCreatedEvent)
        : CatalogOutboxEntity {
            val objectMapper = ObjectMapperFixtures.nullableObjectMapper()
            return CatalogOutboxEntity(
                createdAt = OffsetDateTime.now(),
                sagaId = orderCreatedEvent.sagaId,
                sagaStatus = SagaStatus.CREATED,
                outboxStatus = OutboxStatus.CREATED,
                eventType = EventType.ORDER_CREATED,
                payload = objectMapper.writeValueAsString(orderCreatedEvent)
            )
        }

        fun fromNullOrderCreatedEvent()
        : CatalogOutboxEntity {
            return CatalogOutboxEntity(
                createdAt = OffsetDateTime.now(),
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
                createdAt = OffsetDateTime.now(),
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

        fun randomCreatedEntityList5() : List<CatalogOutboxEntity>{
            return OrderCreatedEventFixtures
                .randomEventList5()
                .map { orderCreatedEvent ->  fromOrderCreatedEvent(orderCreatedEvent)}
        }
    }

}