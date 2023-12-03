package net.spring.cloud.prototype.orderservice.fixtures

import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.orderservice.dataaccess.entity.OrderEntity
import java.math.BigInteger
import java.time.OffsetDateTime

class OrderEntityFixtures {

    companion object {
        fun randomOrderEntityList5() : List<OrderEntity>{
            return listOf(
                randomOrderEntity(),
                randomOrderEntity(),
                randomOrderEntity(),
                randomOrderEntity(),
                randomOrderEntity(),
            )
        }

        fun randomOrderEntity() : OrderEntity {
            return OrderEntity(
                userId = UlidCreator.monotonicUuid(),
                productId = UlidCreator.monotonicUuid(),
                qty = BigInteger.TWO,
                unitPrice = BigInteger.valueOf(1000),
                totalPrice = BigInteger.valueOf(50000),
                createdAt = OffsetDateTime.now(),
            )
        }
    }

}