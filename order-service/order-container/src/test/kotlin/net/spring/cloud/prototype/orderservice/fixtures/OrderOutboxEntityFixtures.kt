package net.spring.cloud.prototype.orderservice.fixtures

import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.orderservice.domain.outbox.entity.OrderOutboxEntity
import java.time.ZonedDateTime

class OrderOutboxEntityFixtures {

    companion object{
        fun fromOrderCreatedEvent(orderCreatedEvent: OrderCreatedEvent)
        : OrderOutboxEntity{
            val objectMapper = ObjectMapperFixtures.nullableObjectMapper()
            return OrderOutboxEntity(
                createdAt = ZonedDateTime.now(),
                sagaId = orderCreatedEvent.sagaId,
                sagaStatus = SagaStatus.CREATED,
                outboxStatus = OutboxStatus.CREATED,
                eventType = EventType.ORDER_CREATED,
                payload = objectMapper.writeValueAsString(orderCreatedEvent)
            )
        }

        fun randomEntity(eventString : String) : OrderOutboxEntity {
            return OrderOutboxEntity(
                sagaId = UlidCreator.monotonicUuid(),
                sagaStatus = SagaStatus.CREATED,
                outboxStatus = OutboxStatus.CREATED,
                eventType = EventType.ORDER_CREATED,
                payload = eventString
            )
        }

        fun randomCreatedEntityList5() : List<OrderOutboxEntity>{
            val objectMapper = ObjectMapperFixtures.nullableObjectMapper()
            return OrderCreatedEventFixtures
                .randomEventList5()
                .map{ orderCreatedEvent -> fromOrderCreatedEvent(orderCreatedEvent) }
                .map{ orderOutboxEntity -> randomEntity(objectMapper.writeValueAsString(orderOutboxEntity)) }
        }
    }

}