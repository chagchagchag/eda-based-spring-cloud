package net.spring.cloud.prototype.userservice.application.valueobject

data class ReadUserResponse (
    val id : String,
    val email: String,
    val name: String,
){
}