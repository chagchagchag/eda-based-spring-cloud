package net.spring.cloud.prototype.catalogservice.dataaccess.repository

import net.spring.cloud.prototype.catalogservice.dataaccess.entity.CatalogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CatalogJpaRepository : JpaRepository<CatalogEntity, UUID>{
    fun findByProductId(productId: UUID) : CatalogEntity?
}