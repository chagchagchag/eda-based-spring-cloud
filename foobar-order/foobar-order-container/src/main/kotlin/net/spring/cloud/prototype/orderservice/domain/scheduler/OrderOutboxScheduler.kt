package net.spring.cloud.prototype.orderservice.domain.scheduler

import net.spring.cloud.prototype.orderservice.domain.outbox.OrderOutboxHelper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OrderOutboxScheduler (
    val orderOutboxHelper: OrderOutboxHelper,
){
    @Scheduled(
        fixedDelayString = "2000",
        initialDelayString = "10000",
    )
    fun publishOrderCreatedEvent(){
        orderOutboxHelper.publishOrderCreatedEvent()
    }
}