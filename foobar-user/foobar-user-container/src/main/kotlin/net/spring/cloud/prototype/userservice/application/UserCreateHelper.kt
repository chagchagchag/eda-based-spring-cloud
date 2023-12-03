package net.spring.cloud.prototype.userservice.application

import net.spring.cloud.prototype.userservice.dataaccess.dto.UserDto
import net.spring.cloud.prototype.userservice.domain.dto.create.SignupUserCommand

interface UserCreateHelper {
    fun signup(signupUserCommand: SignupUserCommand): UserDto
    fun getUserDetailsByEmail(email: String): UserDto
}