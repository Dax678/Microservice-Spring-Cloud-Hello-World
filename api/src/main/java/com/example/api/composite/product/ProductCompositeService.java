package com.example.api.composite.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ProductComposite", description = "REST API for composite product information")
public interface ProductCompositeService {

    @Operation(
            summary = "Get ProductAggregate by Id",
            description = "Description"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.badRequest.description}")
    })
    @GetMapping(value = "/product-composite/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductAggregate getProduct(@PathVariable Long productId);

    @Operation(
            summary = "Add new product",
            description = "Description"
    )
    @PostMapping(value = "/product-composite", consumes = MediaType.APPLICATION_JSON_VALUE)
    void createProduct(@RequestBody ProductAggregate product);

    @Operation(
            summary = "Delete product by Id",
            description = "Description"
    )
    @DeleteMapping(value = "/product-composite/{productId}")
    void deleteProduct(@PathVariable Long productId);
}
