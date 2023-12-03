package net.spring.cloud.prototype.catalogservice.config

import net.spring.cloud.prototype.catalogservice.config.properties.CatalogServiceProperties
import net.spring.cloud.prototype.catalogservice.config.properties.KafkaConsumerProperties
import net.spring.cloud.prototype.catalogservice.config.properties.KafkaProducerProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    KafkaConsumerProperties::class,
    KafkaProducerProperties::class,
    CatalogServiceProperties::class,
)
class EnableConfigurationPropertiesConfig {
}