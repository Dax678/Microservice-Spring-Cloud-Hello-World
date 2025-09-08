package com.example.api.core.review;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ReviewService {
    @GetMapping(value="/review/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Review> getReviews(@PathVariable Long productId);
}