package net.spring.cloud.prototype.userservice.application

import net.spring.cloud.prototype.userservice.application.mapper.UserCommandMapper
import net.spring.cloud.prototype.userservice.dataaccess.repository.UserJpaRepository
import net.spring.cloud.prototype.userservice.domain.dto.UserDto
import net.spring.cloud.prototype.userservice.domain.dto.create.SignupUserCommand
import net.spring.cloud.prototype.userservice.dataaccess.UserDataMapper
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserCreateHelperImpl(
    val userJpaRepository: UserJpaRepository,
    val userDataMapper: UserDataMapper,
    val userCommandMapper: UserCommandMapper,
) : UserCreateHelper {

    @Transactional
    override fun signup(signupUserCommand: SignupUserCommand): UserDto {
        val userEntity = userCommandMapper.fromSignupUserCommand(signupUserCommand)
        val entity = userJpaRepository.save(userEntity)
        return userDataMapper.ofUserDto(entity)
    }

    override fun getUserDetailsByEmail(email: String): UserDto {
        val userEntity = userJpaRepository.findByEmail(email) ?: throw UsernameNotFoundException(email)
        return userDataMapper.ofUserDto(userEntity)
    }
}