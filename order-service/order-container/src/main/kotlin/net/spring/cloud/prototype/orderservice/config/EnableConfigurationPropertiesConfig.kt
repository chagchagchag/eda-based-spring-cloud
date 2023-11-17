package net.spring.cloud.prototype.orderservice.config

import net.spring.cloud.prototype.orderservice.config.properties.KafkaConsumerProperties
import net.spring.cloud.prototype.orderservice.config.properties.KafkaProducerProperties
import net.spring.cloud.prototype.orderservice.config.properties.OrderServiceProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    KafkaConsumerProperties::class,
    KafkaProducerProperties::class,
    OrderServiceProperties::class,
)
class EnableConfigurationPropertiesConfig {
}