package net.spring.cloud.prototype.userservice.application

import net.spring.cloud.prototype.userservice.dataaccess.repository.UserJpaRepository
import net.spring.cloud.prototype.userservice.dataaccess.dto.UserDto
import net.spring.cloud.prototype.userservice.dataaccess.UserDataMapper
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserSagaHelper (
    val userJpaRepository: UserJpaRepository,
    val userDataMapper: UserDataMapper,
){
    fun findUserByUserId(userId: String): UserDto {
        return userJpaRepository
            .findById(UUID.fromString(userId))
            .map { userDataMapper.ofUserDto(it) }
            .orElseThrow { UsernameNotFoundException("User Not Found") }
    }

    fun findUserByEmail(email: String): UserDto? {
        return userJpaRepository
            .findByEmail(email)
            ?.let { userDataMapper.ofUserDto(it) }
    }
}