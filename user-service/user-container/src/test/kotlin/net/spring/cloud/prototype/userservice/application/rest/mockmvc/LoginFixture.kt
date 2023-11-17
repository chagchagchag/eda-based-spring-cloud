package net.spring.cloud.prototype.userservice.application.rest.mockmvc

import net.spring.cloud.prototype.userservice.domain.dto.UserDto
import java.time.LocalDate
import java.util.UUID

class LoginFixture {

    companion object {
        fun ofUserDto(
            email: String,
            name: String,
            password: String,
        ) : UserDto {
            return UserDto(
                email = email,
                name = name,
                encryptedPassword = password,
                createdAt = LocalDate.now(),
                id = UUID.randomUUID().toString(),
                roles = ""
            )
        }
    }
}