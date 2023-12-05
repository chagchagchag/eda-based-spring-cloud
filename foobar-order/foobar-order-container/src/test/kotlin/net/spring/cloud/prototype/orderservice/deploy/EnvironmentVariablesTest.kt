package net.spring.cloud.prototype.orderservice.deploy

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EnvironmentVariablesTest {

    @Test
    @DisplayName("환경변수 `COMPOSE_SPRING_PROFILES_ACTIVE` 확인")
    fun 환경변수_확인(){
        Assertions
            .assertThat(System.getenv("COMPOSE_SPRING_PROFILES_ACTIVE"))
            .isEqualTo("compose")
    }

}