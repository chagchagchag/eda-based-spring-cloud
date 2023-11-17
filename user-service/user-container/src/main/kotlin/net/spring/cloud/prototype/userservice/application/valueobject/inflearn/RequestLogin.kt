package net.spring.cloud.prototype.userservice.application.valueobject.inflearn

import org.jetbrains.annotations.NotNull

data class RequestLogin (
    @NotNull
    val email: String,
    val password: String,
){
}