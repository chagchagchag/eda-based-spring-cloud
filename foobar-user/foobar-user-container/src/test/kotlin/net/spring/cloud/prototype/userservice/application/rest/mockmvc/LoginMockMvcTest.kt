package net.spring.cloud.prototype.userservice.application.rest.mockmvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import jakarta.servlet.http.HttpServletResponse
import net.spring.cloud.prototype.userservice.application.rest.UsersController
import net.spring.cloud.prototype.userservice.dataaccess.user.repository.UserJpaRepository
import net.spring.cloud.prototype.userservice.application.UserApplicationServiceImpl
import net.spring.cloud.prototype.userservice.application.UserCreateHelperImpl
import net.spring.cloud.prototype.userservice.application.UserSagaHelper
import net.spring.cloud.prototype.userservice.application.mapper.UserRequestMapper
import net.spring.cloud.prototype.userservice.application.mapper.UserResponseMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.env.Environment
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@WebMvcTest
@ExtendWith(MockitoExtension::class)
class LoginMockMvcTest {

    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userApplicationService : UserApplicationServiceImpl

    @MockBean
    private lateinit var userCreateHelper: UserCreateHelperImpl

    @MockBean
    private lateinit var userSagaHelper: UserSagaHelper

    @MockBean
    private lateinit var userResponseMapper: UserResponseMapper

    @MockBean
    private lateinit var userRequestMapper: UserRequestMapper

    @Mock
    private lateinit var environment: Environment

    @Mock
    private lateinit var httpServletResponse: HttpServletResponse

    @MockBean
    private lateinit var userJpaRepository: UserJpaRepository

    private val objectMapper = ObjectMapper().registerModule(
        KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, true)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )

    @BeforeEach
    fun init(){
        mockMvc = MockMvcBuilders
            .standaloneSetup(
                UsersController(
                userApplicationService = userApplicationService,
                userRequestMapper = userRequestMapper,
                environment = environment,
            )
            )
            .build()
    }

    @Test
    fun `로그인 (정상케이스)`(){
        // Spring Cloud 기반 MSA 구현을 위해 통합테스트(.http파일 테스트)만 거친 후 MockMvc 테스트는 잠시 미뤄둠.
    }

}