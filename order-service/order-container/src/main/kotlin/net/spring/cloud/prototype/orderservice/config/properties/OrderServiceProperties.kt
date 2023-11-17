package net.spring.cloud.prototype.orderservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "order-service")
data class OrderServiceProperties(
    val serviceName: String,
    val orderCreatedEventTopicName: String,
)
