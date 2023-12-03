package net.spring.cloud.prototype.catalogservice.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import net.spring.cloud.prototype.domain.converter.EventConverter
import net.spring.cloud.prototype.domain.event.OrderCreatedEvent
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ObjectMapperUtils (
    @Qualifier("nullableObjectMapper")
    val nullableObjectMapper: ObjectMapper
){

    fun <T> serializeObject(data : T) : String {
        return nullableObjectMapper.writeValueAsString(data)
    }

    fun fromEventString(eventString: String) : OrderCreatedEvent {
        return EventConverter.fromEventString(nullableObjectMapper, eventString)
    }

}