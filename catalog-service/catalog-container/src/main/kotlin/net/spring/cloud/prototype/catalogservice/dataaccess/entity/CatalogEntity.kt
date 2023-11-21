package net.spring.cloud.prototype.catalogservice.dataaccess.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import net.spring.cloud.prototype.dataaccess.entity.PrimaryKeyEntity
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "catalog")
class CatalogEntity(
    productId: UUID,
    productName: String,
    stock: BigInteger,
    unitPrice: BigInteger,
    createdAt: OffsetDateTime = OffsetDateTime.now(),
) : PrimaryKeyEntity(){

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val productId = productId

    @Column(nullable = false, length = 120, unique = false)
    var productName: String = productName

    @Column(nullable = false)
    var stock: BigInteger = stock

    @Column(nullable = false)
    var unitPrice: BigInteger = unitPrice

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP")
    val createdAt: OffsetDateTime = createdAt

    fun decreaseQty(qty : BigInteger){
        stock -= qty
    }
}