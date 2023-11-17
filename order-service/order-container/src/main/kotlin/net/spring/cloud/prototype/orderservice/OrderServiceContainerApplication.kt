package net.spring.cloud.prototype.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = ["net.spring.cloud.prototype"])
class OrderServiceContainerApplication

fun main(args: Array<String>){
    runApplication<net.spring.cloud.prototype.orderservice.OrderServiceContainerApplication>(*args)
}