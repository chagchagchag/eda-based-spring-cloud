package net.spring.cloud.prototype.orderservice.eda_scenario_test.create_order

import io.github.serpro69.kfaker.Faker
import net.spring.cloud.prototype.orderservice.auth.jwt.JwtProcessor
import net.spring.cloud.prototype.orderservice.auth.key.SecurityProperties
import net.spring.cloud.prototype.orderservice.fixtures.JwtCreatCommandFixture
import net.spring.cloud.prototype.orderservice.fixtures.OrderRequestFixtures
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.nio.charset.StandardCharsets

@ActiveProfiles("local-docker")
@SpringBootTest
class CreateOrderScenarioTest {
    @Autowired @Qualifier("passwordEncoder")
    private lateinit var passwordEncoder: PasswordEncoder

    private lateinit var webTestClient: WebTestClient

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
        val faker = Faker()
        val username = faker.artist.names()
        val password = faker.artist.names()
        val jwtCreateCommand = JwtCreatCommandFixture.randomJwtCreateUserCommand(username, password, passwordEncoder)
        val jwtString = JwtProcessor.generateToken(SecurityProperties.key, jwtCreateCommand)

        val orderRequest = OrderRequestFixtures.randomOrderRequest()

        webTestClient
            .post()
            .uri("/order-service/order")
            .accept(MediaType.APPLICATION_JSON)
            .acceptCharset(StandardCharsets.UTF_8)
            .bodyValue(orderRequest)
            .header("Authorization", "Bearer $jwtString")
            .exchange()
            .expectStatus().isCreated
    }
}