package net.spring.cloud.prototype.userservice.application

import jakarta.servlet.http.HttpServletResponse
import net.spring.cloud.prototype.userservice.application.valueobject.LoginRequest
import net.spring.cloud.prototype.userservice.application.valueobject.LoginResponse
import net.spring.cloud.prototype.userservice.application.valueobject.ReadUserResponse
import net.spring.cloud.prototype.userservice.application.valueobject.SignupResponse
import net.spring.cloud.prototype.userservice.dataaccess.dto.create.SignupUserCommand

interface UserApplicationService {
    fun signup(signupUserCommand: SignupUserCommand): SignupResponse
    fun login(loginRequest: LoginRequest, response: HttpServletResponse): LoginResponse
    fun findUserById(userId: String): ReadUserResponse
}