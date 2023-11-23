package net.spring.cloud.prototype.orderservice.fixtures

import io.github.serpro69.kfaker.Faker
import net.spring.cloud.prototype.dataaccess.ulid.UlidCreator
import net.spring.cloud.prototype.orderservice.auth.jwt.JwtCreateCommand
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.OffsetDateTime

class JwtCreatCommandFixture {
    companion object {

        val faker = Faker()

        fun randomJwtCreateUserCommand(username: String, plainPassword: String, passwordEncoder: PasswordEncoder)
        : JwtCreateCommand {
            return JwtCreateCommand(
                email = "$username@dev.com",
                name = username,
                encryptedPassword = passwordEncoder.encode(plainPassword),
                id = UlidCreator.monotonicUuid(),
                createdAt = OffsetDateTime.now(),
                roles = "ADMIN"
            )
        }
    }
}