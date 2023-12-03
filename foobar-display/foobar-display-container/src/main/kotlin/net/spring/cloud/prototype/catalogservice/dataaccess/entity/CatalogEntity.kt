package net.spring.cloud.prototype.catalogservice.dataaccess.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import net.spring.cloud.prototype.dataaccess.entity.PrimaryKeyEntity
import org.hibernate.annotations.ColumnDefault
import java.math.BigInteger
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "catalog")
class CatalogEntity (
    productId: UUID,
    productName: String,
    stock: BigInteger,
    unitPrice: BigInteger,
    createdAt: ZonedDateTime,
) : PrimaryKeyEntity(){

    @Column(nullable = false)
    val productId = productId

    @Column(nullable = false, length = 120, unique = false)
    var productName: String = productName

    @Column(nullable = false)
    var stock: BigInteger = stock

    @Column(nullable = false)
    var unitPrice: BigInteger = unitPrice

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    val createdAt: ZonedDateTime = createdAt

    fun decreaseQty(qty : BigInteger){
        stock -= qty
    }
}