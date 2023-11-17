package net.spring.cloud.prototype.userservice.application.valueobject

data class SignupRequest (
    val email: String,
    val name: String,
    val password: String,
){
}