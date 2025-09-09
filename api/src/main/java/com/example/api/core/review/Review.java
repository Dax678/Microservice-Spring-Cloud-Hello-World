package com.example.api.core.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Review {
    private Long productId;
    private Long reviewId;
    private String author;
    private String subject;
    private String content;
    private String serviceAddress;
}