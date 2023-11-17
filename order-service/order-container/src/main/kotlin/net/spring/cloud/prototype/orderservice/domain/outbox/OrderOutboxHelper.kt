package net.spring.cloud.prototype.orderservice.domain.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import net.spring.cloud.prototype.domain.event.OutboxStatus
import net.spring.cloud.prototype.domain.event.SagaStatus
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.domain.outbox.repository.OrderOutboxRepository
import net.spring.cloud.prototype.orderservice.domain.publisher.OrderCreatedEventPublisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderOutboxHelper (
    val orderOutboxRepository: OrderOutboxRepository,
    @Qualifier("nullableObjectMapper")
    val nullableObjectMapper: ObjectMapper,
    val orderCreatedEventPublisher: OrderCreatedEventPublisher,
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
            .forEach {
                val event = nullableObjectMapper.readValue<OrderCreatedEvent>(it.payload, OrderCreatedEvent::class.java)

                val payload = nullableObjectMapper.writeValueAsString(event)
                val future = orderCreatedEventPublisher.sendEvent(event.sagaId, payload)

                future.whenComplete { sendResult, throwable ->
                    if(throwable == null){
                        val metadata = sendResult.recordMetadata
                        it.outboxStatus = OutboxStatus.FINISHED
                        it.sagaStatus = SagaStatus.FINISHED
                        orderOutboxRepository.save(it)

                        logger.info(
                            "Received successful response from kafka for " +
                            "Topic :: ${metadata.topic()} " +
                            "Partition :: ${metadata.partition()} " +
                            "Offset :: ${metadata.offset()} " +
                            "Timestamp :: ${metadata.timestamp()}"
                        )
                    }
                    else{
                        it.outboxStatus = OutboxStatus.PENDING
                        it.sagaStatus = SagaStatus.PENDING
                        orderOutboxRepository.save(it)

                        logger.error("Error while sending " +
                            "message ==> ${sendResult.producerRecord.value()} to topic order-created-event", throwable
                        )
                    }
                }
            }
    }
}