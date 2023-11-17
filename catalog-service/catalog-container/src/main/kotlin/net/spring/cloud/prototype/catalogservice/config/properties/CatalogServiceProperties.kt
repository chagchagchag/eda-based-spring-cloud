package net.spring.cloud.prototype.catalogservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "catalog-service")
data class CatalogServiceProperties (
    val serviceName: String,
    val orderCreatedEventTopicName: String,
){
}