package net.spring.cloud.prototype.orderservice.dataaccess

import net.spring.cloud.prototype.orderservice.dataaccess.repository.OrderJpaRepository
import net.spring.cloud.prototype.orderservice.fixtures.OrderEntityFixtures
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
class OrderJpaRepositoryTest {

    @Autowired
    lateinit var orderJpaRepository: OrderJpaRepository

    // 엔티티 매핑에 이상이 있는지 검증을 위한 쿼리 검증
    @Test
    @DisplayName("(쿼리 검증) orderJpaRepository.save()")
    fun `(쿼리 검증) orderJpaRepository save()`(){
        val orderEntityList = OrderEntityFixtures.randomOrderEntityList5()

        orderEntityList
            .forEach { orderEntity -> orderJpaRepository.save(orderEntity) }

        assertThat(orderJpaRepository.findAll()).hasSize(5)
    }
}