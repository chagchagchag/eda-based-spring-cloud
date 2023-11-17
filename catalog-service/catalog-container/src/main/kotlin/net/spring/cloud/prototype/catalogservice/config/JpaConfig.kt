package net.spring.cloud.prototype.catalogservice.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["net.spring.cloud.prototype.catalogservice.dataaccess", "net.spring.cloud.prototype.dataaccess"])
@EntityScan(basePackages = ["net.spring.cloud.prototype.catalogservice.dataaccess", "net.spring.cloud.prototype.dataaccess"])
class JpaConfig {
}