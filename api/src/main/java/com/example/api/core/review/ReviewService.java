package com.example.api.core.review;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ReviewService {
    @GetMapping(value="/review/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Review> getReviews(@PathVariable Long productId);

    @PostMapping(
            value = "/review",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Review createReview(@RequestBody Review review);

    @DeleteMapping(value = "/review")
    void deleteReviews(@RequestParam(value = "productId") Long productId);
}