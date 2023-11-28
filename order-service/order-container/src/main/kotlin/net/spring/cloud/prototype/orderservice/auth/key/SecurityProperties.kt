package net.spring.cloud.prototype.orderservice.auth.key

import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.security.Key

class SecurityProperties {
    companion object{
        val SECRET : String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMLOPQRTTTTTTTTT"
        val key : Key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
    }
}