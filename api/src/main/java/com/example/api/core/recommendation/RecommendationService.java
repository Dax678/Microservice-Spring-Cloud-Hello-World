package com.example.api.core.recommendation;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RecommendationService {
    @GetMapping(value="/recommendation/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Recommendation> getRecommendations(@PathVariable Long productId);

    @PostMapping(
            value = "/recommendation",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Recommendation createRecommendation(@RequestBody Recommendation recommendation);

    @DeleteMapping(value = "/recommendation")
    void deleteRecommendations(@RequestParam(value = "productId") Long productId);
}