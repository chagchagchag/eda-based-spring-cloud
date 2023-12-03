package net.spring.cloud.prototype.catalogservice.application.rest

import net.spring.cloud.prototype.catalogservice.application.CatalogApplicationService
import net.spring.cloud.prototype.catalogservice.application.valueobject.ReadCatalogResponse
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/catalog-service")
class CatalogController(
    val environment: Environment,
    val catalogApplicationService: CatalogApplicationService,
){
    @GetMapping("/health-check")
    fun status() : String {
        return StringBuilder()
            .append("It's Catalog Service. It's running on Port ${environment.getProperty("local.server.port")}")
            .toString()
    }

    @GetMapping("/catalog/product/{productId}")
    fun findOneProduct(
        @PathVariable productId: UUID
    ) : ResponseEntity<ReadCatalogResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(catalogApplicationService.findOneProduct(productId))
    }

}