package com.example.api.core.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Recommendation {
    private final Long id;
    private final String author;
    private final String content;
    private final int rate;
    private final String serviceAddress;
}