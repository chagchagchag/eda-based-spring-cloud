package net.spring.cloud.prototype.domain.event

enum class SagaStatus {
    CREATED, PROCESSING, FINISHED, PENDING,
}