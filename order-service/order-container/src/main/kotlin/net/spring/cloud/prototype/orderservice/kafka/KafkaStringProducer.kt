package net.spring.cloud.prototype.orderservice.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class KafkaStringProducer (
    val kafkaTemplate: KafkaTemplate<String, String>,
    @Qualifier("nullableObjectMapper")
    val nullableObjectMapper: ObjectMapper,
){

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun <T> send(topicName: String, key: String, value: T)
    : CompletableFuture<SendResult<String, String>> {
        val serialized = nullableObjectMapper.writeValueAsString(value)
        return kafkaTemplate.send(topicName, key, serialized)
    }

}