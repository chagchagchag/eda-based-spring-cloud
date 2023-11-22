package net.spring.cloud.prototype.orderservice.auth.jwt

import java.util.*

data class ParsedJwt(
    val id: String,
    val email: String,
    val username: String,
    val password: String,
    val expiration: Date,
){
    companion object {
        fun of(id : String, email: String,  username: String, password: String, expiration: Date) : ParsedJwt
            = ParsedJwt(id, email, username, password, expiration)
    }
}
