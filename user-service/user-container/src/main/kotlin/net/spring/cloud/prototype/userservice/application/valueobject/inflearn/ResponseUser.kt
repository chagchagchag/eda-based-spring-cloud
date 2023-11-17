package net.spring.cloud.prototype.userservice.application.valueobject.inflearn

data class ResponseUser (
    val email: String,
    val name: String,
    val userId: String,
    val orders: List<ResponseOrder>,
){
}