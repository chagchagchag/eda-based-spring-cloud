package net.spring.cloud.prototype.catalogservice.domain.listener

import net.spring.cloud.prototype.catalogservice.domain.CatalogDomainService
import org.springframework.kafka.annotation.KafkaListener
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
        message: String,
    ){
        catalogDomainService.persistOrderCreatedEvent(message)
    }

}