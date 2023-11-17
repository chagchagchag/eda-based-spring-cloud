package net.spring.cloud.prototype.orderservice.domain

import com.fasterxml.jackson.databind.ObjectMapper
import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand
import net.spring.cloud.prototype.orderservice.domain.outbox.entity.OrderOutboxEntity
import net.spring.cloud.prototype.orderservice.domain.outbox.repository.OrderOutboxRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OrderDomainServiceImpl (
    val orderOutboxRepository: OrderOutboxRepository,
    @Qualifier("nullableObjectMapper") val objectMapper: ObjectMapper,
): OrderDomainService {
    override fun orderCreatedEvent(command: CreateOrderCommand): OrderCreatedEvent {
        val event = OrderCreatedEvent(
            orderId = command.orderId,
            userId = command.userId,
            productId = command.productId,
            qty = command.qty,
            unitPrice = command.unitPrice,
            totalPrice = command.totalPrice,
            createdAt = command.createdAt,
        )

        val entity = OrderOutboxEntity(
            sagaStatus = SagaStatus.CREATED,
            outboxStatus = OutboxStatus.CREATED,
            eventType = EventType.ORDER_CREATED,
            payload = objectMapper.writeValueAsString(event)
        )
        orderOutboxRepository.save(entity)
        return event
    }
}