package net.spring.cloud.prototype.orderservice.auth.jwt

import java.time.OffsetDateTime
import java.util.UUID

data class JwtCreateCommand(
    val email : String,
    val name : String,
    val encryptedPassword : String,
    val id: UUID,
    val createdAt: OffsetDateTime,
    val roles: String,
)