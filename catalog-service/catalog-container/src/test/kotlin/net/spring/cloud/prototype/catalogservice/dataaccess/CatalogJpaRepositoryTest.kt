package net.spring.cloud.prototype.catalogservice.dataaccess

import net.spring.cloud.prototype.catalogservice.dataaccess.repository.CatalogJpaRepository
import net.spring.cloud.prototype.catalogservice.fixtures.CatalogEntityFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("docker-mysql")
@Transactional
@SpringBootTest
class CatalogJpaRepositoryTest {

    @Autowired
    lateinit var catalogJpaRepository: CatalogJpaRepository

    @Test
    @DisplayName("(쿼리 검증) findByProductId")
    fun `(쿼리 검증) findByProductId`(){
        val catalogList = listOf(
            CatalogEntityFixtures.randomCatalogEntity(),
            CatalogEntityFixtures.randomCatalogEntity(),
            CatalogEntityFixtures.randomCatalogEntity(),
            CatalogEntityFixtures.randomCatalogEntity(),
            CatalogEntityFixtures.randomCatalogEntity(),
        )
        .map { catalogEntity ->
            catalogJpaRepository.save(catalogEntity)
            catalogEntity
        }

        catalogList.forEach {catalogEntity ->
            val productId = catalogEntity.productId
            val sqlResult = catalogJpaRepository.findByProductId(productId)
            assertThat(sqlResult).isNotNull()
        }

    }

}