package net.spring.cloud.prototype.userservice.config.security.constant

import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.security.Key

class SecurityProperties {
    companion object{
        val SECRET : String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMLOPQRTTTTTTTTT"
        val key : Key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
    }
}