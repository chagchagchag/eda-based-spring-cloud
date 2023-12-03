package net.spring.cloud.prototype.userservice.application.rest.mockmvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import net.spring.cloud.prototype.userservice.application.rest.UsersController
import net.spring.cloud.prototype.userservice.dataaccess.repository.UserJpaRepository
import net.spring.cloud.prototype.userservice.application.UserApplicationServiceImpl
import net.spring.cloud.prototype.userservice.application.UserCreateHelperImpl
import net.spring.cloud.prototype.userservice.application.UserSagaHelper
import net.spring.cloud.prototype.userservice.application.mapper.UserRequestMapper
import net.spring.cloud.prototype.userservice.application.mapper.UserResponseMapper
import net.spring.cloud.prototype.userservice.application.valueobject.SignupRequest
import net.spring.cloud.prototype.userservice.application.valueobject.SignupResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@WebMvcTest
@ExtendWith(MockitoExtension::class)
class SignupMockMvcTest {

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
    fun `회원가입 (Normal)`(){
        Mockito
            .`when`(userApplicationService.signup(any()))
            .thenReturn(SignupResponse("asdf", "asdf@gmail.com", "encoded"))

        val signupRequest = SignupRequest(
            name = "asdf",
            email = "asdf@gmail.com",
            password = "1234"
        )
        val requestBody = objectMapper.writeValueAsString(signupRequest)
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo{MockMvcResultHandlers.print()}
            .andExpect(MockMvcResultMatchers.status().isCreated())
    }

}