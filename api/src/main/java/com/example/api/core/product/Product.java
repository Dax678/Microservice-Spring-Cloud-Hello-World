package com.example.api.core.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Product {
    private Long productId;
    private String name;
    private int weight;
    private String serviceAddress;
}