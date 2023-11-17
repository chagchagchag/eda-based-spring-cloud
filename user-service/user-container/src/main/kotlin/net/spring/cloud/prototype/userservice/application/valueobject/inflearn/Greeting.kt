package net.spring.cloud.prototype.userservice.application.valueobject.inflearn

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class Greeting (
    @Value("\${greeting.message}") private val message: String,
){
}