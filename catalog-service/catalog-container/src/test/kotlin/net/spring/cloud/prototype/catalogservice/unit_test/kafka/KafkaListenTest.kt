package net.spring.cloud.prototype.catalogservice.unit_test.kafka

import net.spring.cloud.prototype.catalogservice.fixtures.OrderCreatedEventFixtures
import net.spring.cloud.prototype.catalogservice.kafka.KafkaStringProducer
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.fixtures.ObjectMapperFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("embedded")
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = [
        "listeners=PLAINTEXT://localhost:39092",
        "port=39092"
    ]
)
class KafkaListenTest {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val orderCreatedTopic = "order-created-event-test-embedded"

    @Autowired
    private lateinit var kafkaStringProducer: KafkaStringProducer

    @Autowired
    private lateinit var testOrderCreatedEventListener: TestOrderCreatedEventListener

    val testObjectMapper = ObjectMapperFixtures.nullableObjectMapper()

    @Test
    @DisplayName("테이터 수신 테스트")
    fun `데이터 수신 테스트`(){
        // given
        //  Random 하게 생성한 OrderCreatedEvent + eventString
        val orderCreatedEvent = OrderCreatedEventFixtures.randomNewOrderCreatedEvent()
        val eventString = testObjectMapper.writeValueAsString(orderCreatedEvent)

        // when
        //  event 데이터를 카프카의 'order-created-event-test' 토픽으로 전송
        kafkaStringProducer.send(orderCreatedTopic, orderCreatedEvent.sagaId.toString(), eventString)
        Thread.sleep(3000)
        logger.info(">>> 데이터 수신 완료 :: ${testOrderCreatedEventListener.received}")
        val received = testOrderCreatedEventListener.received

        // then
        val event = testObjectMapper.readValue<OrderCreatedEvent>(received, OrderCreatedEvent::class.java)
        assertThat(event.sagaId).isEqualTo(orderCreatedEvent.sagaId)
        assertThat(event.orderId).isEqualTo(orderCreatedEvent.orderId)
        assertThat(event.productId).isEqualTo(orderCreatedEvent.productId)
        assertThat(event.totalPrice).isEqualTo(orderCreatedEvent.totalPrice)
        assertThat(event.unitPrice).isEqualTo(orderCreatedEvent.unitPrice)
        assertThat(event.createdAt).isEqualTo(orderCreatedEvent.createdAt)
    }

}