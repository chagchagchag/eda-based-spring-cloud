package net.spring.cloud.prototype.userservice.application.mapper

import net.spring.cloud.prototype.userservice.dataaccess.dto.UserDto
import net.spring.cloud.prototype.userservice.application.valueobject.LoginResponse
import net.spring.cloud.prototype.userservice.application.valueobject.ReadUserResponse
import net.spring.cloud.prototype.userservice.application.valueobject.SignupResponse
import org.springframework.stereotype.Component

@Component
class UserResponseMapper {

    fun ofSignupResponse(userDto: UserDto): SignupResponse =
        SignupResponse(
            email = userDto.email,
            name = userDto.name,
            id = userDto.id
        )

    fun ofReadUserResponse(userDto: UserDto): ReadUserResponse =
        ReadUserResponse(
            id = userDto.id,
            email = userDto.email,
            name = userDto.name
        )

    fun ofLoginResponse(userDto: UserDto): LoginResponse =
        LoginResponse(
            email = userDto.email
        )
}