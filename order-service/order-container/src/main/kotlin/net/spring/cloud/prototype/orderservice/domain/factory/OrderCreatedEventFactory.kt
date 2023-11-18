package net.spring.cloud.prototype.orderservice.domain.factory

import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import net.spring.cloud.prototype.orderservice.domain.outbox.entity.OrderOutboxEntity
import net.spring.cloud.prototype.orderservice.kafka.ObjectMapperUtils
import org.springframework.stereotype.Component

@Component
class OrderCreatedEventFactory (
    val objectMapperUtils: ObjectMapperUtils,
){

    fun fromOrderDto(orderDto: OrderDto): OrderCreatedEvent {
        return OrderCreatedEvent(
            orderId = orderDto.orderId,
            userId = orderDto.userId,
            productId = orderDto.productId,
            qty = orderDto.qty,
            unitPrice = orderDto.unitPrice,
            totalPrice = orderDto.totalPrice,
            createdAt = orderDto.createdAt,
        )
    }

    fun fromCreateOrderCommand(command: CreateOrderCommand) : OrderCreatedEvent {
        return OrderCreatedEvent(
            orderId = command.orderId,
            userId = command.userId,
            productId = command.productId,
            qty = command.qty,
            unitPrice = command.unitPrice,
            totalPrice = command.totalPrice,
            createdAt = command.createdAt,
        )
    }

    fun toOutboxEntity(event: OrderCreatedEvent) : OrderOutboxEntity {
        return OrderOutboxEntity(
            sagaStatus = SagaStatus.CREATED,
            outboxStatus = OutboxStatus.CREATED,
            eventType = EventType.ORDER_CREATED,
            payload = objectMapperUtils.serializeObject(event)
        )
    }

}