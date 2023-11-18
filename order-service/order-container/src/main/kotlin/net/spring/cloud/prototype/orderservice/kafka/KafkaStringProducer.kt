package net.spring.cloud.prototype.orderservice.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class KafkaStringProducer (
    val kafkaTemplate: KafkaTemplate<String, String>,
    val objectMapperUtils: ObjectMapperUtils,
){

    fun <T> send(topicName: String, key: String, value: T)
    : CompletableFuture<SendResult<String, String>> {
        val serialized = objectMapperUtils.serializeObject(value)
        return kafkaTemplate.send(topicName, key, serialized)
    }

}