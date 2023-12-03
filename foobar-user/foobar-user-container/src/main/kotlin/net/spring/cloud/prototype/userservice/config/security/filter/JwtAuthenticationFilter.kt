package net.spring.cloud.prototype.userservice.config.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.spring.cloud.prototype.userservice.config.security.constant.SecurityProperties
import net.spring.cloud.prototype.userservice.config.security.jwt.JwtProcessor
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtAuthenticationFilter (
    private val authenticationManager: AuthenticationManager,
) : OncePerRequestFilter (){
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader : String? = request.getHeader(HttpHeaders.AUTHORIZATION)

        // TODO : 테스트 가능하도록 변경 필요
        authorizationHeader?.let{ headerString ->
            if(JwtProcessor.checkContainsBearer(headerString)){
                val parsedJwt = JwtProcessor.degenerateToken(SecurityProperties.key, headerString)

                if(JwtProcessor.checkIfExpired(parsedJwt.expiration)){
                    // TODO : RestControllerAdvice 처리 할 것.
                    //  만료일자가 도래했을 때 StatusCode :302, 메시지는 "로그인기한이 만료되었습니다. 다시 로그인해주세요", ErrorCode : "JWT_EXPIRED"
                    throw IllegalStateException("로그인 기한이 만료되었습니다. 다시 로그인해주세요.")
                }

                val auth = UsernamePasswordAuthenticationToken(parsedJwt.email, parsedJwt.password)
                authenticationManager.authenticate(auth)
                SecurityContextHolder.getContext().authentication = auth
            }
        }

        filterChain.doFilter(request, response)
    }


}