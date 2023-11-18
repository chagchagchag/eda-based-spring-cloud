package net.spring.cloud.prototype.domain.converter

import com.fasterxml.jackson.databind.ObjectMapper

class EventConverter {

    companion object{
        inline fun<reified T> fromEventString (objectMapper: ObjectMapper, payload:String) : T {
            return objectMapper.readValue<T>(payload, T::class.java)
        }
    }

}