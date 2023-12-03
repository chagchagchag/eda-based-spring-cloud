package net.spring.cloud.prototype.userservice.config.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.spring.cloud.prototype.userservice.application.valueobject.LoginRequest
import net.spring.cloud.prototype.userservice.config.security.constant.SecurityProperties
import net.spring.cloud.prototype.userservice.config.security.jwt.JwtProcessor
import net.spring.cloud.prototype.userservice.config.security.userdetails.CustomUserDetails
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.RuntimeException

class JwtAuthenticationFilterOld (
    private val authenticationManager: AuthenticationManager
): UsernamePasswordAuthenticationFilter(){
    private val logger= LoggerFactory.getLogger(javaClass)

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        return request
            ?.let { httpServletRequest ->
                LoginRequest.of(httpServletRequest)
                    .map { loginRequest ->
                        val authenticationToken = loginRequest.generateAuthenticationToken()
                        val authentication = authenticationManager.authenticate(authenticationToken)
                        authentication
                    }
                    .orElseThrow {
                        IllegalArgumentException("사용자 정보가 부정확 합니다.")
                    }
            }
            ?: throw RuntimeException("HttpServletRequest is Null !!!")
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val userDetails = authResult?.principal as CustomUserDetails
        val jwtToken = JwtProcessor.generateToken(SecurityProperties.key, userDetails)
        response
            ?.addHeader("Authorization", "Bearer $jwtToken")
            ?: throw IllegalStateException("Empty Response")
    }
}