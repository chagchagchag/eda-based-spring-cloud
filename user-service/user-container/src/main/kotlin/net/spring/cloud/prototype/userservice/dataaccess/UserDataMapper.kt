package net.spring.cloud.prototype.userservice.dataaccess

import net.spring.cloud.prototype.userservice.dataaccess.entity.UserEntity
import net.spring.cloud.prototype.userservice.dataaccess.dto.UserDto
import net.spring.cloud.prototype.userservice.application.valueobject.inflearn.ResponseOrder
import net.spring.cloud.prototype.userservice.application.valueobject.inflearn.ResponseUser
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

    fun ofUserResponse(userEntity: UserEntity): ResponseUser =  ResponseUser(
        email = userEntity.email,
        name = userEntity.name,
        userId = userEntity.id.toString(),
        orders = listOf<ResponseOrder>(),
    )

    fun ofUserResponseList(userList: Iterable<UserEntity>): List<ResponseUser> {
        val list = mutableListOf<ResponseUser>()

        userList.forEach { list.add(ofUserResponse(it)) }

        return list.toList()
    }
}