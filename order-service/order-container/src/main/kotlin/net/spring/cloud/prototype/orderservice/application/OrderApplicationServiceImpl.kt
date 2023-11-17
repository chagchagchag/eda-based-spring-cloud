package net.spring.cloud.prototype.orderservice.application

import net.spring.cloud.prototype.orderservice.dataaccess.CreateOrderCommand
import net.spring.cloud.prototype.orderservice.application.mapper.OrderResponseMapper
import net.spring.cloud.prototype.orderservice.application.valueobject.OrderCreatedResponse
import org.springframework.stereotype.Service

@Service
class OrderApplicationServiceImpl(
    private val orderCommandHandler: OrderCommandHandler,
    private val orderResponseMapper: OrderResponseMapper,
) : OrderApplicationService {
    override fun createOrder(createOrderCommand: CreateOrderCommand): OrderCreatedResponse {
        return orderResponseMapper
            .toOrderCreatedResponse(
                orderCommandHandler.createOrder(createOrderCommand)
            )
    }
}