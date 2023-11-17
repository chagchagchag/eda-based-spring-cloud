package net.spring.cloud.prototype.orderservice.application

import net.spring.cloud.prototype.orderservice.application.mapper.OrderDataMapper
import net.spring.cloud.prototype.orderservice.application.valueobject.ResponseOrder
import net.spring.cloud.prototype.orderservice.dataaccess.repository.OrderJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderSagaHelper (
    val orderJpaRepository: OrderJpaRepository,
    val orderDataMapper: OrderDataMapper,
){
    fun getOrderByUserId(userId: String): List<ResponseOrder> {
        return orderDataMapper.ofOrderResponseList(orderJpaRepository.findByUserId(UUID.fromString(userId)))
    }
}