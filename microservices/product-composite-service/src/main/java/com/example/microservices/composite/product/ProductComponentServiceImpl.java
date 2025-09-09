package com.example.microservices.composite.product;

import com.example.api.composite.product.*;
import com.example.api.core.product.Product;
import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.review.Review;
import com.example.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductComponentServiceImpl implements ProductCompositeService {
    private final ServiceUtil serviceUtil;
    private final ProductCompositeIntegration productCompositeIntegration;

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

    @Override
    public void createProduct(ProductAggregate body) {
        try {
            Product product = new Product(body.getProductId(), body.getName(), body.getWeight(), null);
            productCompositeIntegration.createProduct(product);

            if(body.getRecommendations() != null) {
                body.getRecommendations().forEach(r -> {
                    Recommendation recommendation = new Recommendation(body.getProductId(), r.getRecommendationId(), r.getAuthor(), r.getContent(), r.getRate(), null);
                    productCompositeIntegration.createRecommendation(recommendation);
                });
            }

            if(body.getReviews() != null) {
                body.getReviews().forEach(r -> {
                    Review review = new Review(body.getProductId(), r.getReviewId(), r.getAuthor(), r.getSubject(), r.getContent(), null);
                    productCompositeIntegration.createReview(review);
                });
            }
        } catch(HttpClientErrorException e) {
            throw e;
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        productCompositeIntegration.deleteProduct(productId);
        productCompositeIntegration.deleteRecommendations(productId);
        productCompositeIntegration.deleteReviews(productId);
    }

    private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendationList, List<Review> reviewList, String serviceAddress) {
        Long productId = product.getProductId();
        String name = product.getName();
        int weight = product.getWeight();

        List<RecommendationSummary> recommendationSummaries =
                (recommendationList == null) ? new ArrayList<>() : recommendationList.stream()
                        .map(recommendation -> new RecommendationSummary(recommendation.getRecommendationId(), recommendation.getAuthor(), recommendation.getRate(), recommendation.getContent()))
                        .toList();
        List<ReviewSummary> reviewSummaries =
                (reviewList == null) ? new ArrayList<>() : reviewList.stream()
                        .map(review -> new ReviewSummary(review.getReviewId(), review.getAuthor(), review.getSubject(), review.getContent()))
                        .toList();
        String productAddress = product.getServiceAddress();
        String recommendationAddress = (recommendationList != null && !recommendationList.isEmpty()) ? recommendationList.getFirst().getServiceAddress() : "";
        String reviewAddress = (reviewList != null && !reviewList.isEmpty()) ? reviewList.getFirst().getServiceAddress() : "";

        ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress,productAddress,recommendationAddress,reviewAddress);

        return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries, serviceAddresses);
    }


}
