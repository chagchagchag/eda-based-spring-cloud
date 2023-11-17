package net.spring.cloud.prototype.userservice.application.valueobject

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.*

data class LoginRequest(
    val email: String,
    val password: String,
){
    fun generateAuthenticationToken(): Authentication
        = UsernamePasswordAuthenticationToken(
            email, password
        )

    companion object {
        private val objectMapper : ObjectMapper = ObjectMapper()
        fun of(request: HttpServletRequest) : Optional<LoginRequest> {
            try {
                return Optional
                    .ofNullable(
                        objectMapper.readValue(request.inputStream, LoginRequest::class.java)
                    )
            }
            catch (e: Exception){
                e.printStackTrace()
                throw RuntimeException(e)
            }
            return Optional.empty()
        }

    }
}
