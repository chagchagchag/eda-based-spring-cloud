package net.spring.cloud.prototype.orderservice.domain

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand

interface OrderDomainService {

    fun orderCreatedEvent(command: CreateOrderCommand) : OrderCreatedEvent
}