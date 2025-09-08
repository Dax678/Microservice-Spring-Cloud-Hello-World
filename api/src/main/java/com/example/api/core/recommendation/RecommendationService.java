package com.example.api.core.recommendation;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface RecommendationService {
    @GetMapping(value="/recommendation/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Recommendation> getRecommendations(@PathVariable Long productId);
}