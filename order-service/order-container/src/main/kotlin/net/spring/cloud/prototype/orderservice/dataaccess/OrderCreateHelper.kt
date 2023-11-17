package net.spring.cloud.prototype.orderservice.dataaccess

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent


interface OrderCreateHelper {
    fun createOrder(createOrderCommand: CreateOrderCommand): OrderCreatedEvent
}