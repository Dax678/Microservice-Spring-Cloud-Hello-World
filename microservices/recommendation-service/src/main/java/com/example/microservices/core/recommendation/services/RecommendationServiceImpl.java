package com.example.microservices.core.recommendation.services;

import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.recommendation.RecommendationService;
import com.example.api.exceptions.InvalidInputException;
import com.example.microservices.core.recommendation.persistence.RecommendationEntity;
import com.example.microservices.core.recommendation.persistence.RecommendationRepository;
import com.example.util.http.ServiceUtil;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationServiceImpl implements RecommendationService {
    private final Logger logger = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper mapper;
    private final ServiceUtil serviceUtil;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, RecommendationMapper recommendationMapper, ServiceUtil serviceUtil) {
        this.recommendationRepository = recommendationRepository;
        this.mapper = recommendationMapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Recommendation> getRecommendations(Long productId) {
        if(productId < 1) throw new InvalidInputException("Product id must be greater than zero");

        List<RecommendationEntity> entityList = recommendationRepository.findAllByProductId(productId);
        List<Recommendation> recommendations = mapper.entityListToApiList(entityList);

        recommendations.forEach(r -> {
            r.setServiceAddress(serviceUtil.getServiceAddress());
        });

        return recommendations;
    }

    @Override
    public Recommendation createRecommendation(Recommendation recommendation) {
        try {
            RecommendationEntity entity = mapper.apiToEntity(recommendation);
            RecommendationEntity newEntity = recommendationRepository.save(entity);

            return mapper.entityToApi(newEntity);
        } catch (DuplicateKeyException ex) {
            throw new InvalidInputException("Duplicate Key");
        }
    }

    @Override
    public void deleteRecommendations(Long productId) {
        recommendationRepository.deleteAll(recommendationRepository.findAllByProductId(productId));
    }
}
