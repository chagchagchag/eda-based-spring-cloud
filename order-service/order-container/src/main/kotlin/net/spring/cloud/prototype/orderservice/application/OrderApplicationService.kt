package net.spring.cloud.prototype.orderservice.application

import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand
import net.spring.cloud.prototype.orderservice.application.valueobject.OrderCreatedResponse

interface OrderApplicationService {
    fun createOrder(createOrderCommand: CreateOrderCommand): OrderCreatedResponse
}