package net.spring.cloud.prototype.domain.event

import com.github.f4b6a3.ulid.UlidCreator
import java.util.UUID

abstract class OrderEvent (
    val eventType: EventType,
    val sagaId: UUID = UlidCreator.getMonotonicUlid().toUuid()
)