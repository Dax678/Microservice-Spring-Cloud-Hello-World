package com.example.microservices.core.recommendation;

import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.recommendation.RecommendationService;
import com.example.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecommendationServiceImpl implements RecommendationService {
    private final Logger logger = LoggerFactory.getLogger(RecommendationServiceImpl.class);
    private final ServiceUtil serviceUtil;

    public RecommendationServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Recommendation> getRecommendations(Long productId) {
        logger.debug("getRecommendations called with productId={}", productId);

        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(new Recommendation(1L, "Recommendation Author 1", "Recommendation Subject 1", 5, serviceUtil.getServiceAddress()));
        recommendations.add(new Recommendation(2L, "Recommendation Author 2", "Recommendation Subject 1", 3, serviceUtil.getServiceAddress()));
        recommendations.add(new Recommendation(3L, "Recommendation Author 2", "Recommendation Subject 2", 4, serviceUtil.getServiceAddress()));
        recommendations.add(new Recommendation(4L, "Recommendation Author 3", "Recommendation Subject 3", 4, serviceUtil.getServiceAddress()));
        return recommendations;
    }
}
