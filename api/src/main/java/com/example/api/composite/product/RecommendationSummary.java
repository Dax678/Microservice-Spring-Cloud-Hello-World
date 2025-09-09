package com.example.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecommendationSummary {
    private final Long recommendationId;
    private final String author;
    private final int rate;
    private final String content;
}
