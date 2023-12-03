package net.spring.cloud.prototype.orderservice.unit_test.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderCreatedEventTestConsumer {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    lateinit var receivedString : String
    lateinit var key : String
    lateinit var value : String

    @KafkaListener(
        id = "order-created-topic-test-consumer",
        topics = ["order-created-event-test"],
    )
    fun receive(consumerRecord: ConsumerRecord<String, String>){
        receivedString = consumerRecord.toString()
        key = consumerRecord.key()
        value = consumerRecord.value()

        logger.info("""
            >>> 테스트 데이터 수신 : $receivedString
        """.trimIndent())
    }
}