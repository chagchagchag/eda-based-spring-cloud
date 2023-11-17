package net.spring.cloud.prototype.userservice.application.mapper

import net.spring.cloud.prototype.userservice.dataaccess.entity.UserEntity
import net.spring.cloud.prototype.userservice.domain.dto.create.SignupUserCommand
import org.springframework.stereotype.Component

@Component
class UserCommandMapper {
    fun fromSignupUserCommand(signupUserCommand: SignupUserCommand) : UserEntity {
        return UserEntity(
            email = signupUserCommand.email,
            name = signupUserCommand.name,
            encryptedPassword = signupUserCommand.encryptedPassword,
            roles = "ROLE_USER"
        )
    }
}