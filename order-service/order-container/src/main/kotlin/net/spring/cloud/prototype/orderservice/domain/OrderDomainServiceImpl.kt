package net.spring.cloud.prototype.orderservice.domain

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand
import net.spring.cloud.prototype.orderservice.domain.factory.OrderCreatedEventFactory
import net.spring.cloud.prototype.orderservice.domain.outbox.repository.OrderOutboxRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderDomainServiceImpl (
    val orderOutboxRepository: OrderOutboxRepository,
    val orderCreatedEventFactory: OrderCreatedEventFactory,
): OrderDomainService {

    private val logger : Logger = LoggerFactory.getLogger(javaClass)

    override fun saveOrderCreatedEventAtOutbox(command: CreateOrderCommand): OrderCreatedEvent {
        val event = orderCreatedEventFactory.fromCreateOrderCommand(command)
        val entity = orderCreatedEventFactory.toOutboxEntity(event)
        orderOutboxRepository.save(entity)
        return event
    }
}