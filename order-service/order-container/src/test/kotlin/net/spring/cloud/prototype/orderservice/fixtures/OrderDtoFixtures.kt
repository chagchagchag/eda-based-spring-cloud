package net.spring.cloud.prototype.orderservice.fixtures

import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import java.math.BigInteger
import java.time.LocalDate

class OrderDtoFixtures {

    companion object {
        fun whenOrderCreated() : OrderDto{
            return OrderDto(
                orderId = UlidCreator.monotonicUuid(),
                userId = UlidCreator.monotonicUuid(),
                productId = UlidCreator.monotonicUuid(),
                qty = BigInteger.TEN,
                unitPrice = BigInteger.valueOf(1000),
                totalPrice = BigInteger.valueOf(30000),
                createdAt = LocalDate.now(),
            )
        }
    }

}