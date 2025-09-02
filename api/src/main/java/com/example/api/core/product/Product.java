package com.example.api.core.product;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Product {
    private final Long productId;
    private final String name;
    private final int weight;
    private final String serviceAddress;
}