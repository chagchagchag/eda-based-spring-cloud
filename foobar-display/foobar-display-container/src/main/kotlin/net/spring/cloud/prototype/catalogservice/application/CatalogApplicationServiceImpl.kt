package net.spring.cloud.prototype.catalogservice.application

import net.spring.cloud.prototype.catalogservice.application.valueobject.ReadCatalogResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class CatalogApplicationServiceImpl (
    val catalogSagaHelper: CatalogSagaHelper,
): CatalogApplicationService {
    override fun findOneProduct(productId: UUID): ReadCatalogResponse {
        return catalogSagaHelper.findOneProduct(productId)
    }
}