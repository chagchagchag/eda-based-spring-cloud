package net.spring.cloud.prototype.catalogservice.domain.scheduler

import net.spring.cloud.prototype.catalogservice.domain.CatalogOutboxHelper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CatalogOutboxScheduler (
    val catalogOutboxHelper: CatalogOutboxHelper,
){

    @Scheduled(
        fixedRateString = "3000",
        initialDelayString = "10000",
    )
    fun handleOrderCreatedEvent(){
        catalogOutboxHelper.handleOrderCreatedEvent()
    }

}