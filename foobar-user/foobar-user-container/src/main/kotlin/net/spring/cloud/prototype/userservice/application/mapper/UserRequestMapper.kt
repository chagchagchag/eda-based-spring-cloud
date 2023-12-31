package net.spring.cloud.prototype.userservice.application.mapper

import net.spring.cloud.prototype.userservice.application.valueobject.SignupRequest
import net.spring.cloud.prototype.userservice.dataaccess.dto.create.SignupUserCommand
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserRequestMapper (
    val passwordEncoder: PasswordEncoder
){
    fun toSignupUserCommand(signupRequest: SignupRequest) : SignupUserCommand
        = SignupUserCommand(
        password = signupRequest.password,
        name = signupRequest.name,
        email = signupRequest.email,
        createdAt = LocalDate.now(),
        encryptedPassword = passwordEncoder.encode(signupRequest.password),
    )
}