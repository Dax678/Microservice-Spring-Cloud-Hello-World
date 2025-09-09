package com.example.api.core.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Recommendation {
    private Long productId;
    private Long recommendationId;
    private String author;
    private String content;
    private int rate;
    private String serviceAddress;
}