package net.spring.cloud.prototype.userservice.dataaccess

import net.spring.cloud.prototype.userservice.dataaccess.entity.UserEntity
import net.spring.cloud.prototype.userservice.dataaccess.dto.UserDto
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserDataMapper (
){
    fun ofUserDto(userEntity: UserEntity): UserDto = UserDto(
        id = userEntity.id.toString(),
        email = userEntity.email,
        name = userEntity.name,
        encryptedPassword = userEntity.encryptedPassword,
        createdAt = LocalDate.now(),
        roles = userEntity.roles,
    )
}