package com.example.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewSummary {
    private final Long id;
    private final String author;
    private final String subject;
}
