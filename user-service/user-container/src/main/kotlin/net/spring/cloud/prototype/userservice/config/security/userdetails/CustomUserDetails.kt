package net.spring.cloud.prototype.userservice.config.security.userdetails

import net.spring.cloud.prototype.userservice.dataaccess.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails (
    val user: UserEntity
): UserDetails{
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        user.getRoleList().forEach { authorities.add { it } }
        return authorities
    }

    override fun getPassword(): String {
        return user.encryptedPassword
    }

    override fun getUsername(): String {
        return user.name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}