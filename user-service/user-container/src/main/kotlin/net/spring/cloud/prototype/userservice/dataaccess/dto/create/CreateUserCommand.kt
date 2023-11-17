package net.spring.cloud.prototype.userservice.domain.dto.create

import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.userservice.application.valueobject.inflearn.RequestUser
import net.spring.cloud.prototype.userservice.application.valueobject.inflearn.ResponseOrder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

data class CreateUserCommand(
    val id: String,
    val password: String,
    val name: String,
    val email: String,
    val createdAt: LocalDate = LocalDate.now(),
    val decryptedPassword: String,
    val encryptedPassword: String,
    val orders: List<ResponseOrder> = listOf<ResponseOrder>()
){

    companion object {
        fun ofCreateUserCommand(requestUser: RequestUser, passwordEncoder: PasswordEncoder): CreateUserCommand
            = CreateUserCommand(
                id = UlidCreator.monotonicUuid().toString(),
                password = requestUser.password,
                name = requestUser.name,
                email = requestUser.email,
                createdAt = LocalDate.now(),
                decryptedPassword = requestUser.password,
                encryptedPassword = passwordEncoder.encode(requestUser.password),
                orders = listOf<ResponseOrder>()
            )
    }

}