package net.spring.cloud.prototype.userservice.config.security.userdetails

import net.spring.cloud.prototype.userservice.dataaccess.entity.UserEntity
import net.spring.cloud.prototype.userservice.dataaccess.repository.UserJpaRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsServiceImpl (
    val userJpaRepository: UserJpaRepository
): CustomUserDetailsService{
    override fun loadUserByUsername(email: String?): UserDetails {
        if(email == null) throw UsernameNotFoundException("email is Empty")
        val userEntity : UserEntity = userJpaRepository.findByEmail(email) ?: throw UsernameNotFoundException("$email : not found")

        return User(
            userEntity.email,
            userEntity.encryptedPassword,
            true,true,true,true,
            mutableListOf<GrantedAuthority>()
        )
    }
}