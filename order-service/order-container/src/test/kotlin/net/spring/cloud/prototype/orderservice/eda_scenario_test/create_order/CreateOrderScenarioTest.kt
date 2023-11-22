package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import io.github.serpro69.kfaker.Faker
import net.spring.cloud.prototype.orderservice.auth.jwt.JwtProcessor
import net.spring.cloud.prototype.orderservice.fixtures.JwtCreatCommandFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@ActiveProfiles("docker-mysql")
@SpringBootTest
class CreateOrderScenarioTest {


    private lateinit var webTestClient: WebTestClient

    @Autowired @Qualifier("passwordEncoder")
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun init(){
        webTestClient = WebTestClient
            .bindToServer()
            .baseUrl("http://localhost:28085")
            .build()
    }

    @Test
    @DisplayName("주문생성 → 주문저장 → 도메인이벤트 생성 & outbox 저장 → 스케쥴러로 꺼낸 후 Kafka Push")
    fun `주문생성 요청 → 주문저장 → 도메인이벤트 생성 & outbox 저장 → 스케쥴러로 꺼낸 후 Kafka Push`(){
//        val faker = Faker()
//        val password = faker.artist.names()
//        val jwtCreateCommand = JwtCreatCommandFixture.randomJwtCreateUserCommand(password, passwordEncoder)
//        JwtProcessor.generateToken(jwtCreateCommand)

    }
}