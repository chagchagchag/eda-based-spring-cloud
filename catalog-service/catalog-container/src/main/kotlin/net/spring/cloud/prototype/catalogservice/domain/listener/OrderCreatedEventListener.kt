package net.spring.cloud.prototype.catalogservice.domain.listener

import net.spring.cloud.prototype.catalogservice.domain.CatalogDomainService
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class OrderCreatedEventListener (
    val catalogDomainService: CatalogDomainService
){

    @KafkaListener(
        id = "order-created-topic-consumer",
        topics = ["order-created-event"]
    )
    fun listenOrderCreatedEvent(
        @Payload messages: List<OrderCreatedEvent>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>,
    ){
        messages.forEach {
            catalogDomainService.persistOrderCreatedEvent(it)
        }
    }

}