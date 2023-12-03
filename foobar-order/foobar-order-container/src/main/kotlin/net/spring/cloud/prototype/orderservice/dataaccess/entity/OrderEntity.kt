package net.spring.cloud.prototype.orderservice.dataaccess.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import net.spring.cloud.prototype.dataaccess.entity.PrimaryKeyEntity
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "orders")
class OrderEntity(
    userId: UUID,
    productId: UUID,
    qty: BigInteger,
    unitPrice: BigInteger,
    totalPrice: BigInteger,
    createdAt: OffsetDateTime = OffsetDateTime.now(),
) : PrimaryKeyEntity(){

    @Column(columnDefinition = "BINARY(16)", nullable = false, length = 120)
    val userId = userId

    @Column(columnDefinition = "BINARY(16)", nullable = false, length = 120)
    val productId = productId

    @Column(nullable = false)
    var qty: BigInteger = qty
        protected set

    @Column(nullable = false)
    var unitPrice: BigInteger = unitPrice
        protected set

    @Column(nullable = false)
    var totalPrice: BigInteger = totalPrice
        protected set

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP")
    val createdAt: OffsetDateTime = createdAt
}