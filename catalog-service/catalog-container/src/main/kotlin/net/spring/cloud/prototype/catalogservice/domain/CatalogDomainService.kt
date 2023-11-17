package net.spring.cloud.prototype.catalogservice.domain

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent

interface CatalogDomainService {
    fun persistOrderCreatedEvent(orderCreatedEvent: OrderCreatedEvent)
}