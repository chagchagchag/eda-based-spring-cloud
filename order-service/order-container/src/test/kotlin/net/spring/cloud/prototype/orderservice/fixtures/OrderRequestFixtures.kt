package net.spring.cloud.prototype.orderservice.fixtures

import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.orderservice.application.valueobject.OrderRequest
import java.math.BigInteger

class OrderRequestFixtures {

    companion object {
        fun randomOrderRequest(): OrderRequest {
            return OrderRequest(
                productId = UlidCreator.monotonicUuid().toString(),
                qty = BigInteger.TWO,
                unitPrice = BigInteger.valueOf(1000)
            )
        }
    }

}