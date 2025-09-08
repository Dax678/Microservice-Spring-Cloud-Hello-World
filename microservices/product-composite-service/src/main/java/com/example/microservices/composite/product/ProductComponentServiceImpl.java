package com.example.microservices.composite.product;

import com.example.api.composite.product.*;
import com.example.api.core.product.Product;
import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.review.Review;
import com.example.util.http.ServiceUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductComponentServiceImpl implements ProductCompositeService {
    private final ServiceUtil serviceUtil;
    private ProductCompositeIntegration productCompositeIntegration;

    @Autowired
    public ProductComponentServiceImpl(ServiceUtil serviceUtil, ProductCompositeIntegration productCompositeIntegration) {
        this.serviceUtil = serviceUtil;
        this.productCompositeIntegration = productCompositeIntegration;
    }

    @Override
    public ProductAggregate getProduct(Long productId) {
        Product product = productCompositeIntegration.getProduct(productId);
        List<Recommendation> recommendationList = productCompositeIntegration.getRecommendations(productId);
        List<Review> reviewList = productCompositeIntegration.getReviews(productId);

        return createProductAggregate(product, recommendationList, reviewList, serviceUtil.getServiceAddress());
    }

    private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendationList, List<Review> reviewList, String serviceAddress) {
        Long productId = product.getProductId();
        String name = product.getName();
        int weight = product.getWeight();

        List<RecommendationSummary> recommendationSummaries =
                (recommendationList == null) ? new ArrayList<>() : recommendationList.stream()
                        .map(recommendation -> new RecommendationSummary(recommendation.getId(), recommendation.getAuthor(), recommendation.getRate()))
                        .toList();
        List<ReviewSummary> reviewSummaries =
                (reviewList == null) ? new ArrayList<>() : reviewList.stream()
                        .map(review -> new ReviewSummary(review.getId(), review.getAuthor(), review.getSubject()))
                        .toList();
        String productAddress = product.getServiceAddress();
        String recommendationAddress = (recommendationList != null && !recommendationList.isEmpty()) ? recommendationList.getFirst().getServiceAddress() : "";
        String reviewAddress = (reviewList != null && !reviewList.isEmpty()) ? reviewList.getFirst().getServiceAddress() : "";

        ServiceAddresses serviceAddreses = new ServiceAddresses(serviceAddress,productAddress,recommendationAddress,reviewAddress);

        return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries, serviceAddreses);
    }
}
