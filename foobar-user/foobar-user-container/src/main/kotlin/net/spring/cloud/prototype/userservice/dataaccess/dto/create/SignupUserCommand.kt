package net.spring.cloud.prototype.userservice.dataaccess.dto.create

import java.time.LocalDate

data class SignupUserCommand (
    val password: String,
    val name: String,
    val email: String,
    val createdAt: LocalDate = LocalDate.now(),
    val encryptedPassword: String,
){
}