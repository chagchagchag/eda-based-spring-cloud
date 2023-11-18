package net.spring.cloud.prototype.orderservice.dataaccess

import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import net.spring.cloud.prototype.orderservice.application.mapper.OrderCommandMapper
import net.spring.cloud.prototype.orderservice.application.mapper.OrderDataMapper
import net.spring.cloud.prototype.orderservice.dataaccess.entity.OrderEntity
import net.spring.cloud.prototype.orderservice.dataaccess.repository.OrderJpaRepository
import net.spring.cloud.prototype.orderservice.domain.OrderDomainService
import org.springframework.stereotype.Service

@Service
class OrderCreateHelperImpl(
    val orderJpaRepository: OrderJpaRepository,
    val orderDataMapper: OrderDataMapper,
    val orderDomainService: OrderDomainService,
    val orderCommandMapper: OrderCommandMapper,
) : OrderCreateHelper {
    override fun createOrder(createOrderCommand: CreateOrderCommand) : OrderCreatedEvent {
        val orderDto = orderCommandMapper.toOrderDto(createOrderCommand)
        val orderEntity: OrderEntity = orderDataMapper.toOrderEntity(orderDto)
        val saved = orderJpaRepository.save(orderEntity)
        return orderDomainService.saveOrderCreatedEventAtOutbox(createOrderCommand)
    }
}