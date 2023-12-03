package net.spring.cloud.prototype.catalogservice.fixtures

import io.github.serpro69.kfaker.Faker
import net.spring.cloud.prototype.catalogservice.dataaccess.entity.CatalogEntity
import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import java.math.BigInteger
import java.time.OffsetDateTime

class CatalogEntityFixtures {

    companion object{
        val faker = Faker()

        fun randomCatalogEntity() : CatalogEntity{
            return CatalogEntity(
                productId = UlidCreator.monotonicUuid(),
                productName = faker.book.title(),
                stock = BigInteger.TEN,
                unitPrice = BigInteger.valueOf(1000),
                createdAt = OffsetDateTime.now(),
            )
        }

    }

}