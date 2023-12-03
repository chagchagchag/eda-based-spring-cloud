package net.spring.cloud.prototype.orderservice.dataaccess.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import net.spring.cloud.prototype.dataaccess.entity.PrimaryKeyEntity
import java.math.BigInteger
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "orders")
class OrderEntity(
    userId: UUID,
    productId: UUID,
    qty: BigInteger,
    unitPrice: BigInteger,
    totalPrice: BigInteger,
    createdAt: LocalDate,
) : PrimaryKeyEntity(){

    @Column(nullable = false)
    val userId = userId

    @Column(nullable = false, length = 120, unique = true)
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

    @Column(nullable = false, updatable = false, insertable = false)
//    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    val createdAt: LocalDate = createdAt
}