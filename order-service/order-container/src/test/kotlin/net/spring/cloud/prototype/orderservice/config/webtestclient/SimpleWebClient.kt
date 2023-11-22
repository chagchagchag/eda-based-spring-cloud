package net.spring.cloud.prototype.orderservice.config.webtestclient

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Configuration
class SimpleWebClient (
){
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Bean(name = ["localWebClient"])
    fun localWebClient(): WebClient {

        return WebClient.builder()
            .baseUrl("http://localhost:28085")
            .filters{
                it.add(
                    ExchangeFilterFunction.ofResponseProcessor {
                        logHeaders(it)
                        logBody(it)
                    }
                )
            }
            .build()
    }

    fun logHeaders(response: ClientResponse) : Unit{
        response.headers().asHttpHeaders()
            .forEach{
                val headerName = it.key
                val headerValues = it.value

                headerValues.forEach {
                    val headerValue = it
                    logKeyValuePair(headerName, headerValue)
                }
            }
    }

    fun logBody(response : ClientResponse) : Mono<ClientResponse> {
        with(response){
            if(statusCode().is4xxClientError() || statusCode().is5xxServerError()){
                return response.bodyToMono(String.javaClass)
                    .flatMap {
                        println("Body ====> $it")
                        logger.info("Body = $it")
                        Mono.just(response)
                    }
            }
            else{
                return Mono.just(response)
            }
        }
    }

    fun logKeyValuePair(key: String, value: String){
        logger.info("{$key} = {$value")
        println("{$key} = {$value")
    }

}