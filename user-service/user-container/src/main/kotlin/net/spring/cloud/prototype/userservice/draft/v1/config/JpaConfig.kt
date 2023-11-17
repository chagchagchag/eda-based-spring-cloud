package net.spring.cloud.prototype.userservice.draft.v1.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["net.spring.cloud.prototype.userservice.dataaccess", "net.spring.cloud.prototype.dataaccess"])
@EntityScan(basePackages = ["net.spring.cloud.prototype.userservice.dataaccess", "net.spring.cloud.prototype.dataaccess"])
class JpaConfig {
}