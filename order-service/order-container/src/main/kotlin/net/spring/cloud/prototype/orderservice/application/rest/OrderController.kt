package net.spring.cloud.prototype.orderservice.application.rest

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import net.spring.cloud.prototype.orderservice.application.OrderApplicationService
import net.spring.cloud.prototype.orderservice.application.OrderSagaHelper
import net.spring.cloud.prototype.orderservice.application.mapper.OrderRequestMapper
import net.spring.cloud.prototype.orderservice.application.valueobject.OrderCreatedResponse
import net.spring.cloud.prototype.orderservice.application.valueobject.OrderRequest
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.nio.charset.StandardCharsets
import java.security.Key


@RestController
@RequestMapping("/order-service")
class OrderController (
    val orderApplicationService: OrderApplicationService,
    val orderSagaHelper: OrderSagaHelper,
    val environment: Environment,
    val orderRequestMapper: OrderRequestMapper,
){
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/health-check")
    fun status() : String{
        return StringBuilder()
            .append("It's Ok. The Service running on Port ${environment.getProperty("local.server.port")}")
            .toString()
    }

    // TODO JWT 분해 로직 공통화 필요
    @PostMapping("/order")
    fun createOrder(
        @RequestBody orderRequest: OrderRequest,
        @RequestHeader requestHeader: HttpHeaders
    ): ResponseEntity<OrderCreatedResponse>{
        requestHeader["Authorization"]
            ?.let{
                val bearerLen = "Bearer ".length

                val headerStr = it.get(0)
                val jwt = headerStr.substring(0, bearerLen)

                // todo 아래 평문 시크릿 프로퍼티스에 공통화해두기 (지금은 시간이 없어서 패스)
                val secret = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMLOPQRTTTTTTTTT"
                val key : Key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

                val parser: JwtParser = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()

                val claimsJws : Jws<Claims> = parser.parseClaimsJws(jwt)
                val userId = claimsJws.body["id"] as String

                val orderCreatedResponse = orderApplicationService.createOrder(
                    orderRequestMapper.toCreateOrderCommand(orderRequest, userId)
                )
                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orderCreatedResponse)
            }
            ?: throw IllegalArgumentException("Authorization Header 는 비어있을 수 없습니다.")
    }
}