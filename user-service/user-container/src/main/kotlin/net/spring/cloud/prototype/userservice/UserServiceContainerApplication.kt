package net.spring.cloud.prototype.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = ["net.spring.cloud.prototype.userservice"])
class UserServiceContainerApplication

fun main(args: Array<String>){
    runApplication<UserServiceContainerApplication>(*args)
}