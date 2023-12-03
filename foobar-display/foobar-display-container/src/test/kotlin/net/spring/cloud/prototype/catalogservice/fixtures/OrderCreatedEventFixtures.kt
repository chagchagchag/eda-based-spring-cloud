package net.spring.cloud.prototype.catalogservice.fixtures

import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import java.math.BigInteger
import java.time.LocalDate

class OrderCreatedEventFixtures {

    companion object {
        fun randomNewOrderCreatedEvent() : OrderCreatedEvent {
            return OrderCreatedEvent(
                orderId = UlidCreator.monotonicUuid(),
                userId = UlidCreator.monotonicUuid(),
                productId = UlidCreator.monotonicUuid(),
                qty = BigInteger.TEN,
                unitPrice = BigInteger.valueOf(1000),
                totalPrice = BigInteger.valueOf(10000),
                createdAt = LocalDate.now(),
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