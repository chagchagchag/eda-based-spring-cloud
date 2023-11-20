package net.spring.cloud.prototype.domain.fixtures

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule

class ObjectMapperFixtures {

    companion object{
        fun nullableObjectMapper(): ObjectMapper {
            val module = KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()

            return ObjectMapper()
                .registerModule(module)
                .registerModule(JavaTimeModule())
        }
    }

}