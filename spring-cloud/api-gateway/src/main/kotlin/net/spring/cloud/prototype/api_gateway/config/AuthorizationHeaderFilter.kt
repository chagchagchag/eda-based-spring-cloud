package net.spring.cloud.prototype.api_gateway.config

import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.lang.Exception

@Component
class AuthorizationHeaderFilter (
    private val environment: Environment
): AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Inner>(
    Inner::class.java
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    class Inner{}

    override fun apply(config: Inner?) = GatewayFilter {
        exchange : ServerWebExchange, chain : GatewayFilterChain ->
            if(!exchange.request.headers.containsKey(HttpHeaders.AUTHORIZATION)){
                onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED)
            }
            else if(!isJwtValid(exchange)){
                onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }
            else{
                chain.filter(exchange)
            }
    }

    fun onError(exchange: ServerWebExchange, err: String, httpStatus: HttpStatus): Mono<Void> {
        val response = exchange.response
        response.setStatusCode(httpStatus)

        logger.error(err)
        return response.setComplete()
    }

    fun isJwtValid(exchange: ServerWebExchange): Boolean{
        val authorizationHeader = exchange.request.headers.get(HttpHeaders.AUTHORIZATION)?.get(0) ?: return false
        val jwt = authorizationHeader.replace("Bearer", "") ?: return false

        try{
            val subject = Jwts.parserBuilder().setSigningKey(environment.getProperty("token.secret"))
                .build()
                .parseClaimsJws(jwt)
                .body.subject

            if(subject == null || subject.isEmpty()) return false
        }
        catch(e : Exception){
            return false
        }

        return true
    }
}