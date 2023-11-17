package net.spring.cloud.prototype.config_server.temp

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class ConfigCommandLineRunner(
    val environment: Environment
) : CommandLineRunner{
    private val logger = LoggerFactory.getLogger(javaClass)
    override fun run(vararg args: String?) {
        logger.debug(">>>>> user.dir = ${environment.getProperty("user.dir")}")
        logger.debug(">>>>> user.home = ${environment.getProperty("user.home")}")
        logger.debug(">>>>> encrypt.key-store.location = ${environment.getProperty("encrypt.key-store.location")}")
    }
}