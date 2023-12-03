package net.spring.cloud.prototype.catalogservice.domain

import net.spring.cloud.prototype.catalogservice.domain.factory.OrderCreatedEventFactory
import net.spring.cloud.prototype.catalogservice.domain.outbox.repository.CatalogOutboxRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CatalogDomainServiceImpl (
    val outboxRepository: CatalogOutboxRepository,
    val orderCreatedEventFactory: OrderCreatedEventFactory,
): CatalogDomainService {

    @Transactional
    override fun persistOrderCreatedEvent(eventString: String) {
        val orderCreatedEvent = orderCreatedEventFactory.fromEventString(eventString)
        val catalogOutboxEntity = orderCreatedEventFactory.toOutboxEntity(orderCreatedEvent)
        outboxRepository.save(catalogOutboxEntity)
    }
}