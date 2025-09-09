package com.example.microservices.core.recommendation.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationRepository extends CrudRepository<RecommendationEntity, Long> {
    List<RecommendationEntity> findAllByProductId(Long productId);
}
