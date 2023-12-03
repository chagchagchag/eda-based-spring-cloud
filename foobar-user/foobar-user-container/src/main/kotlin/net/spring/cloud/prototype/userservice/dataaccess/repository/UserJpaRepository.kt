package net.spring.cloud.prototype.userservice.dataaccess.repository

import net.spring.cloud.prototype.userservice.dataaccess.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity?
}