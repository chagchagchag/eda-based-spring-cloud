package net.spring.cloud.prototype.userservice.application.valueobject

data class SignupResponse(
    val id: String,
    val email: String,
    val name: String,
)
