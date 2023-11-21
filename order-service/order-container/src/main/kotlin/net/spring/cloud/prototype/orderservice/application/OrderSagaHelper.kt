package net.spring.cloud.prototype.orderservice.application

import net.spring.cloud.prototype.orderservice.application.mapper.OrderDataMapper
import net.spring.cloud.prototype.orderservice.dataaccess.repository.OrderJpaRepository
import org.springframework.stereotype.Component

@Component
class OrderSagaHelper (
    val orderJpaRepository: OrderJpaRepository,
    val orderDataMapper: OrderDataMapper,
){

}