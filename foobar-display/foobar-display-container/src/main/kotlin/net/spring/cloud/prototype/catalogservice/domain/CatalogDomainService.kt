package net.spring.cloud.prototype.catalogservice.domain

interface CatalogDomainService {
    fun persistOrderCreatedEvent(eventString: String)
}