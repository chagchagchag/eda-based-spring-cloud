package net.spring.cloud.prototype.orderservice.domain.outbox.repository

import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.orderservice.fixtures.OrderOutboxEntityFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("docker-mysql")
@SpringBootTest
@Transactional
class OrderOutboxRepositoryTest {

    @Autowired
    private lateinit var orderOutboxRepository: OrderOutboxRepository

//    private val mySQLContainer = MySQLContainer("mysql:8")
//
//    @BeforeEach
//    fun init(){
//        mySQLContainer.start()
//    }
//
//    @AfterEach
//    fun destroy(){
//        mySQLContainer.stop()
//    }

    @Test
    @DisplayName("(SQL 검증) OrderOutboxRepository 의 findAllCreatedOrderEvent() 쿼리 검증")
    @Transactional
    fun `(SQL 검증) OrderOutboxRepository 의 findAllCreatedOrderEvent() 쿼리 검증`(){
        val outboxEntityList = OrderOutboxEntityFixtures.randomCreatedEntityList5()
        outboxEntityList
            .forEach { orderOutboxEntity ->
                orderOutboxRepository.save(orderOutboxEntity)
            }

        val list = orderOutboxRepository.findAllCreatedOrderEvent(SagaStatus.CREATED, OutboxStatus.CREATED)
        assertThat(list.size).isEqualTo(5)
    }

}