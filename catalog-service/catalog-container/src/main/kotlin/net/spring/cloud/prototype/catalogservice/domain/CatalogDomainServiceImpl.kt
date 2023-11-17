package net.spring.cloud.prototype.catalogservice.domain

import com.fasterxml.jackson.databind.ObjectMapper
import net.spring.cloud.prototype.catalogservice.domain.outbox.entity.CatalogOutboxEntity
import net.spring.cloud.prototype.catalogservice.domain.outbox.repository.CatalogOutboxRepository
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CatalogDomainServiceImpl (
    val outboxRepository: CatalogOutboxRepository,
    @Qualifier("nullableObjectMapper")
    val objectMapper: ObjectMapper
): CatalogDomainService {

    @Transactional
    override fun persistOrderCreatedEvent(orderCreatedEvent: OrderCreatedEvent) {
        val entity = CatalogOutboxEntity(
            sagaId = orderCreatedEvent.sagaId,
            sagaStatus = SagaStatus.CREATED,
            outboxStatus = OutboxStatus.CREATED,
            eventType = orderCreatedEvent.eventType,
            payload = objectMapper.writeValueAsString(orderCreatedEvent)
        )

        outboxRepository.save(entity)
    }
}