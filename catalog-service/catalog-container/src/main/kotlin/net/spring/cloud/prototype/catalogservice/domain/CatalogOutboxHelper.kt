package net.spring.cloud.prototype.catalogservice.domain

import com.fasterxml.jackson.databind.ObjectMapper
import net.spring.cloud.prototype.catalogservice.dataaccess.repository.CatalogJpaRepository
import net.spring.cloud.prototype.catalogservice.domain.outbox.repository.CatalogOutboxRepository
import net.spring.cloud.prototype.domain.converter.EventConverter
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CatalogOutboxHelper (
    @Qualifier("nullableObjectMapper")
    val nullableObjectMapper: ObjectMapper,
    val catalogOutboxRepository: CatalogOutboxRepository,
    val catalogJpaRepository: CatalogJpaRepository,
){

    @Transactional
    fun handleOrderCreatedEvent(){
        catalogOutboxRepository
            .findAllCreatedOrderEvent(SagaStatus.CREATED, OutboxStatus.CREATED)
            .map {
                it.outboxStatus = OutboxStatus.PROCESSING
                it.sagaStatus = SagaStatus.PROCESSING
                it
            }
            .forEach { outboxEntity ->
                if(outboxEntity.payload == null || StringUtils.isEmpty(outboxEntity.payload))
                    throw IllegalArgumentException("SagaID 에 대해 outboxEntity 가 비어있습니다.")

                val orderCreatedEvent = EventConverter.fromEventString<OrderCreatedEvent>(nullableObjectMapper, outboxEntity.payload!!)

                catalogJpaRepository
                    .findByProductId(orderCreatedEvent.productId)
                    ?.let{ catalogEntity ->
                        catalogEntity.decreaseQty(orderCreatedEvent.qty)
                        catalogJpaRepository.save(catalogEntity)

                        outboxEntity.outboxStatus = OutboxStatus.FINISHED
                        outboxEntity.sagaStatus = SagaStatus.FINISHED
                    }
                    ?: run{
                        outboxEntity.outboxStatus = OutboxStatus.PENDING
                        outboxEntity.sagaStatus = SagaStatus.PENDING
                    }
            }
    }

}