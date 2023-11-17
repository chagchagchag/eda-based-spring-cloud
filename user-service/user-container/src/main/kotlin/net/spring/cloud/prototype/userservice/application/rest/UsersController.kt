package net.spring.cloud.prototype.userservice.application.rest

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.spring.cloud.prototype.userservice.application.UserApplicationService
import net.spring.cloud.prototype.userservice.application.mapper.UserRequestMapper
import net.spring.cloud.prototype.userservice.application.valueobject.*
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST API 규격을 맞춰서 자원의 행위를 HTTP 메서드로 표현하는 것이 정석적인 방법이긴 하지만...
 * User 쪽의 API 는 이런 행위를 표현 하는게 조금은 부자연스럽게 느껴져서
 * 불가피하게 welcome, signup, login, logout 등의 독자적인 URL 을 가지도록 지정함.
 */
@RestController
class UsersController (
    val userApplicationService: UserApplicationService,
    val userRequestMapper: UserRequestMapper,
    val environment: Environment,
){
    @GetMapping("/welcome")
    fun welcome(request: HttpServletRequest, response: HttpServletResponse) : String {
        return "Welcome"
    }

    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest) :  ResponseEntity<SignupResponse> {
        val signupUserCommand = userRequestMapper.toSignupUserCommand(signupRequest)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userApplicationService.signup(signupUserCommand))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest,
        response: HttpServletResponse,
    ) : ResponseEntity<LoginResponse> {
        val loginResponse = userApplicationService.login(loginRequest, response)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loginResponse)
    }

    @GetMapping("/logout")
    fun logout(){

    }

    @GetMapping("/users/{userId}")
    fun findOneById(@PathVariable("userId") userId: String): ResponseEntity<ReadUserResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userApplicationService.findUserById(userId))
    }

    @GetMapping("/health-check")
    fun status(): String {
        return StringBuilder()
            .append("user-service at /health-check")
            .append(", port(local.server.port) = ").append(environment.getProperty("local.server.port"))
            .append(", port(server.port) = ").append(environment.getProperty("server.port"))
            .append(", gateway ip = ").append(environment.getProperty("gateway.ip"))
            .append(", message = ").append(environment.getProperty("greeting.message"))
            .append(", token.secret = ").append(environment.getProperty("token.secret"))
            .append(", token expiration time = ").append(environment.getProperty("token.expiration_time"))
            .toString()
    }

//    @GetMapping("/users")
//    fun getUsers(): ResponseEntity<ReadUsersPagingResponse>{
//    // 페이징 적용 기능으로 추가 예정
//    }
}