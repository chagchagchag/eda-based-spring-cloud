package net.spring.cloud.prototype.orderservice.domain.publisher

import net.spring.cloud.prototype.orderservice.config.properties.OrderServiceProperties
import net.spring.cloud.prototype.orderservice.kafka.KafkaStringProducer
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class OrderCreatedEventPublisher (
    val orderServiceProperties: OrderServiceProperties,
    val kafkaStringProducer: KafkaStringProducer,
){
    fun sendEvent(sagaId: UUID, event: String)
    : CompletableFuture<SendResult<String, String>> {
        val topicName = orderServiceProperties.orderCreatedEventTopicName
        return kafkaStringProducer.send(topicName, sagaId.toString(), event)
    }
}