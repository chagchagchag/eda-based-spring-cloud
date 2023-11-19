package net.spring.cloud.prototype.orderservice.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class KafkaStringProducer(
    val kafkaTemplate: KafkaTemplate<String, String>,
){

    fun send(topicName: String, key: String, value: String)
    : CompletableFuture<SendResult<String, String>> {
        return kafkaTemplate.send(topicName, key, value)
    }

}