package net.spring.cloud.prototype.orderservice.domain.outbox

import net.spring.cloud.prototype.domain.event.EventType
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.orderservice.dataaccess.dto.OrderDto
import net.spring.cloud.prototype.orderservice.domain.factory.OrderCreatedEventFactory
import net.spring.cloud.prototype.orderservice.domain.outbox.repository.OrderOutboxRepository
import net.spring.cloud.prototype.orderservice.domain.publisher.OrderCreatedEventPublisher
import org.apache.commons.lang.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderOutboxHelper (
    val orderOutboxRepository: OrderOutboxRepository,
    val orderCreatedEventPublisher: OrderCreatedEventPublisher,
    val orderCreatedEventFactory: OrderCreatedEventFactory,
){
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun publishOrderCreatedEvent(){
        orderOutboxRepository
            .findAllCreatedOrderEvent(SagaStatus.CREATED, OutboxStatus.CREATED,)
            .map {
                it.outboxStatus = OutboxStatus.PROCESSING
                it.sagaStatus = SagaStatus.PROCESSING
                it
            }
            .forEach { outboxEntity ->
                if(outboxEntity.payload == null || outboxEntity.payload!!.isBlank())
                    throw IllegalArgumentException("SagaID 에 대해 outboxEntity 가 비어있습니다.")

                val future = orderCreatedEventPublisher.sendEvent(outboxEntity.sagaId, outboxEntity.payload!!)

                future.whenComplete { sendResult, throwable ->
                    if(throwable == null){
                        val metadata = sendResult.recordMetadata
                        outboxEntity.outboxStatus = OutboxStatus.FINISHED
                        outboxEntity.sagaStatus = SagaStatus.FINISHED
                        orderOutboxRepository.save(outboxEntity)
                    }
                    else{
                        outboxEntity.outboxStatus = OutboxStatus.PENDING
                        outboxEntity.sagaStatus = SagaStatus.PENDING
                        orderOutboxRepository.save(outboxEntity)

                        logger.error("Error while sending " +
                            "message ==> ${sendResult.producerRecord.value()} to topic order-created-event", throwable
                        )
                    }
                }
            }
    }

    @Transactional
    fun insertToOutbox(orderDto: OrderDto, eventType: EventType) : OrderCreatedEvent {
        val orderCreatedEvent = orderCreatedEventFactory.fromOrderDto(orderDto)
        val orderOutboxEntity = orderCreatedEventFactory.toOutboxEntity(orderCreatedEvent)
        orderOutboxRepository.save(orderOutboxEntity)
        return orderCreatedEvent
    }
}