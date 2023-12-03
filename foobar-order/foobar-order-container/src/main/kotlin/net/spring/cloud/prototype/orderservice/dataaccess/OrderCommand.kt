package net.spring.cloud.prototype.orderservice.dataaccess

interface OrderCommand {
    fun <T> toEvent() : T
}