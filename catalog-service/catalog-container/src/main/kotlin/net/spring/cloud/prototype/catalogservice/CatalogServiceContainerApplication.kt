package net.spring.cloud.prototype.catalogservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = ["net.spring.cloud.prototype"])
class CatalogServiceContainerApplication

fun main(args: Array<String>){
    runApplication<CatalogServiceContainerApplication>(*args)
}