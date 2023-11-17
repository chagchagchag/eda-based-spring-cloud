package net.spring.cloud.prototype.orderservice.domain.publisher

import net.spring.cloud.prototype.orderservice.config.properties.OrderServiceProperties
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class OrderCreatedEventPublisher (
    val kafkaTemplate: KafkaTemplate<String, String>,
    val orderServiceProperties: OrderServiceProperties,
){
    fun sendEvent(sagaId: UUID, value: String)
    : CompletableFuture<SendResult<String, String>> {
        val topicName = orderServiceProperties.orderCreatedEventTopicName
        return kafkaTemplate.send(topicName, sagaId.toString(), value)
    }
}