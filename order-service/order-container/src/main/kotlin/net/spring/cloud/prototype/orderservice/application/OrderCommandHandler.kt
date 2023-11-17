package net.spring.cloud.prototype.orderservice.application

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand
import net.spring.cloud.prototype.orderservice.dataaccess.OrderCreateHelper
import org.springframework.stereotype.Component

@Component
class OrderCommandHandler (
    private val orderCreateHelper: OrderCreateHelper,
){

    fun createOrder(createOrderCommand: CreateOrderCommand): OrderCreatedEvent {
        return orderCreateHelper
            .createOrder(createOrderCommand)
    }

}