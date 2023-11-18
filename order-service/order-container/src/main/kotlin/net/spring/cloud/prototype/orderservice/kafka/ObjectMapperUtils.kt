package net.spring.cloud.prototype.orderservice.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ObjectMapperUtils (
    @Qualifier("nullableObjectMapper")
    val nullableObjectMapper : ObjectMapper,
){

    fun <T> serializeObject(data : T) : String {
        return nullableObjectMapper.writeValueAsString(data)
    }

}