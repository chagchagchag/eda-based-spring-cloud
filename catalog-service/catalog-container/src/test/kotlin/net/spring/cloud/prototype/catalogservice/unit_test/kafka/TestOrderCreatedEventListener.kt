package net.spring.cloud.prototype.catalogservice.unit_test.kafka

import net.spring.cloud.prototype.catalogservice.domain.CatalogDomainService
import net.spring.cloud.prototype.catalogservice.domain.listener.OrderCreatedEventListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TestOrderCreatedEventListener(
    catalogDomainService: CatalogDomainService,
) : OrderCreatedEventListener(
    catalogDomainService = catalogDomainService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    lateinit var messageList: List<String>

    @KafkaListener(
        id = "order-created-topic-test-consumer",
        topics = ["order-created-event-test-embedded"]
    )
    override fun listenOrderCreatedEvent(messages: List<String>) {
        messageList = messages
        logger.info("""
            >>> 테스트 데이터 수신 : $messageList
        """.trimIndent())
    }

}