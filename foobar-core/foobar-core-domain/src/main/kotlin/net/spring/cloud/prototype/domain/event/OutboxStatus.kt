package net.spring.cloud.prototype.domain.event

enum class OutboxStatus {
    CREATED, PROCESSING, FINISHED, PENDING,
}