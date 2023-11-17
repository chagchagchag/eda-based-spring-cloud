package net.spring.cloud.prototype.orderservice.dataaccess.repository

import net.spring.cloud.prototype.orderservice.dataaccess.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderJpaRepository : JpaRepository<OrderEntity, UUID>{
    fun findByUserId(userId: UUID): Iterable<OrderEntity>
}