package com.example.api.core.product;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface ProductService {
    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Product getProduct(@PathVariable Long id);

    @PostMapping(
            value = "/product",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Product createProduct(@RequestBody Product product);

    @DeleteMapping(value = "/product/{productId}")
    void deleteProduct(@PathVariable Long productId);
}