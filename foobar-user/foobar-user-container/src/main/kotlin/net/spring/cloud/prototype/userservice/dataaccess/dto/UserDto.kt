package net.spring.cloud.prototype.userservice.domain.dto

import java.time.LocalDate

data class UserDto(
    val email : String,
    val name : String,
    val encryptedPassword : String,
    val id: String,
    val createdAt: LocalDate,
    val roles: String,
){
}
