package net.spring.cloud.prototype.orderservice.fixtures

import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import java.math.BigInteger
import java.time.OffsetDateTime

class OrderCreatedEventFixtures {
    companion object{
        fun fromOrderDto(orderDto : OrderDto) : OrderCreatedEvent {
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

        fun randomNewOrderCreatedEvent() : OrderCreatedEvent {
            return OrderCreatedEvent(
                orderId = UlidCreator.monotonicUuid(),
                userId = UlidCreator.monotonicUuid(),
                productId = UlidCreator.monotonicUuid(),
                qty = BigInteger.TEN,
                unitPrice = BigInteger.valueOf(1000),
                totalPrice = BigInteger.valueOf(10000),
                createdAt = OffsetDateTime.now(),
            )
        }

        fun randomEventList5() : List<OrderCreatedEvent> {
            return listOf(
                randomNewOrderCreatedEvent(),
                randomNewOrderCreatedEvent(),
                randomNewOrderCreatedEvent(),
                randomNewOrderCreatedEvent(),
                randomNewOrderCreatedEvent(),
            )
        }
    }
}