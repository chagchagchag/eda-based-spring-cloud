package net.spring.cloud.prototype.catalogservice.domain

import net.spring.cloud.prototype.catalogservice.domain.outbox.repository.CatalogOutboxRepository
import net.spring.cloud.prototype.catalogservice.fixtures.CatalogOutboxEntityFixtures
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
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
class CatalogOutboxRepositoryTest {

    @Autowired
    lateinit var catalogOutboxRepository: CatalogOutboxRepository

    @Test
    @DisplayName("(쿼리 검증) findAllCreatedOrderEvent")
    fun `(쿼리 검증) findAllCreatedOrderEvent`(){
        val catalogOutboxList = CatalogOutboxEntityFixtures
            .randomCreatedEntityList5()
            .map{catalogOutboxEntity ->
                catalogOutboxRepository.save(catalogOutboxEntity)
                catalogOutboxEntity
            }

        val resultList = catalogOutboxRepository
            .findAllCreatedOrderEvent(
                SagaStatus.CREATED, OutboxStatus.CREATED
            )

        catalogOutboxList.forEach { catalogOutboxEntity ->
            val foundList = resultList.filter { it.sagaId == catalogOutboxEntity.sagaId }
            assertThat(foundList).hasSize(1)

            assertThat(foundList[0].sagaStatus).isEqualTo(SagaStatus.CREATED)
            assertThat(foundList[0].outboxStatus).isEqualTo(OutboxStatus.CREATED)
        }
    }

}