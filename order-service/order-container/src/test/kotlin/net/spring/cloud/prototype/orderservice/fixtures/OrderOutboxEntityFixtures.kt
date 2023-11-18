package net.spring.cloud.prototype.orderservice.fixtures

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
    }

}