package com.example.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewSummary {
    private final Long reviewId;
    private final String author;
    private final String subject;
    private final String content;
}
