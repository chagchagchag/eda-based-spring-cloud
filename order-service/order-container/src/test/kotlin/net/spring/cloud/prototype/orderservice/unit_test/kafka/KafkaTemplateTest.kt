package net.spring.cloud.prototype.orderservice.unit_test.kafka

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.fixtures.ObjectMapperFixtures
import net.spring.cloud.prototype.orderservice.fixtures.OrderCreatedEventFixtures
import net.spring.cloud.prototype.orderservice.kafka.KafkaStringProducer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = [
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092"
    ]
)
class KafkaTemplateTest {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var orderCreatedEventTestConsumer: OrderCreatedEventTestConsumer

    @Autowired
    private lateinit var kafkaStringProducer: KafkaStringProducer

    val orderCreatedTopic = "order-created-event-test"

    val testObjectMapper = ObjectMapperFixtures.nullableObjectMapper()

    @Test
    fun `데이터 전송 테스트`(){
        // given
        //  Random 하게 생성한 OrderCreatedEvent + eventString
        val orderCreatedEvent = OrderCreatedEventFixtures.randomNewOrderCreatedEvent()
        val eventString = testObjectMapper.writeValueAsString(orderCreatedEvent)

        // when
        //  event 데이터를 카프카의 'order-created-event' 토픽으로 전송
        kafkaStringProducer.send(orderCreatedTopic, orderCreatedEvent.sagaId.toString(), eventString)
        //  수신한 데이터를 변수에 기억
        Thread.sleep(3000)
        logger.info(">>> 데이터 수신 완료 ::: ${orderCreatedEventTestConsumer.receivedString}")
        val receivedString = orderCreatedEventTestConsumer.value
        val receivedEvent = testObjectMapper.readValue<OrderCreatedEvent>(receivedString, OrderCreatedEvent::class.java)

        // then
        // 수신한 데이터가 전송한 데이터와 일치하는지 검사
        assertThat(receivedEvent.sagaId).isEqualTo(orderCreatedEvent.sagaId)
        assertThat(receivedEvent.orderId).isEqualTo(orderCreatedEvent.orderId)
        assertThat(receivedEvent.productId).isEqualTo(orderCreatedEvent.productId)
        assertThat(receivedEvent.totalPrice).isEqualTo(orderCreatedEvent.totalPrice)
        assertThat(receivedEvent.unitPrice).isEqualTo(orderCreatedEvent.unitPrice)
        assertThat(receivedEvent.createdAt).isEqualTo(orderCreatedEvent.createdAt)
    }
}