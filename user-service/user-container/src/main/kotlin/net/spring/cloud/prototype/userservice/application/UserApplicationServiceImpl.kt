package net.spring.cloud.prototype.userservice.application

import jakarta.servlet.http.HttpServletResponse
import net.spring.cloud.prototype.userservice.application.mapper.UserResponseMapper
import net.spring.cloud.prototype.userservice.config.security.constant.SecurityProperties
import net.spring.cloud.prototype.userservice.config.security.jwt.JwtProcessor
import net.spring.cloud.prototype.userservice.domain.dto.create.SignupUserCommand
import net.spring.cloud.prototype.userservice.application.valueobject.LoginRequest
import net.spring.cloud.prototype.userservice.application.valueobject.LoginResponse
import net.spring.cloud.prototype.userservice.application.valueobject.ReadUserResponse
import net.spring.cloud.prototype.userservice.application.valueobject.SignupResponse
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserApplicationServiceImpl(
    private val userCreateHelper: UserCreateHelper,
    private val userSagaHelper: UserSagaHelper,
    private val userResponseMapper: UserResponseMapper,
    private val passwordEncoder: PasswordEncoder,
) : UserApplicationService {
    override fun signup(signupUserCommand: SignupUserCommand): SignupResponse {
        val userDto = userCreateHelper.signup(signupUserCommand)
        return userResponseMapper.ofSignupResponse(userDto)
    }

    // 0. DB의 사용자 패스워드와 ID가 일치하는지 조회한다.
    // 1. JwtProcessor 를 이용해 token 을 만든다
    // 2. response Header 에 token 을 심는다.
    // 3. LoginResponse 를 return 한다.
    override fun login(loginRequest: LoginRequest, response: HttpServletResponse): LoginResponse {
        // 0. DB의 사용자 패스워드와 ID가 일치하는지 조회한다.
        val userDto = userSagaHelper
            .findUserByEmail(loginRequest.email)
            ?: throw UsernameNotFoundException("입력하신 Email로 가입한 회원정보가 존재하지 않습니다.")

        val userPassword = userDto.encryptedPassword

        if(!passwordEncoder.matches(loginRequest.password, userPassword))
            throw UsernameNotFoundException("비밀번호가 부정확합니다.")

        // 1. JwtProcessor 를 이용해 token 을 만든다
        val jwtToken = JwtProcessor.generateToken(SecurityProperties.key, userDto)
        // 2. response Header 에 token 을 심는다.
        JwtProcessor.addJwtAtResponseHeader(jwtToken, response)
        // 3. LoginResponse 를 return 한다.
        return userResponseMapper.ofLoginResponse(userDto)
    }

    override fun findUserById(userId: String): ReadUserResponse {
        val userDto = userSagaHelper.findUserByUserId(userId)
        return userResponseMapper.ofReadUserResponse(userDto)
    }
}