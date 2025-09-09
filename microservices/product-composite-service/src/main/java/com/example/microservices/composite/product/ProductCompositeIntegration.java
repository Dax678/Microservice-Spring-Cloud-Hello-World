package com.example.microservices.composite.product;

import com.example.api.core.product.Product;
import com.example.api.core.product.ProductService;
import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.recommendation.RecommendationService;
import com.example.api.core.review.Review;
import com.example.api.core.review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {
    Logger logger = LoggerFactory.getLogger(ProductCompositeIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    @Autowired
    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.product-service.port}") int productServicePort,
            @Value("${app.recommendation-service.port}") int recommendationServicePort,
            @Value("${app.review-service.port}") int reviewServicePort
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

        this.productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product";
        this.recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation";
        this.reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review";
    }

    @Override
    public Product getProduct(Long productId) {
        String url = productServiceUrl + "/" + productId;
        return restTemplate.getForObject(url, Product.class);
    }

    @Override
    public Product createProduct(Product product) {
        try {
            return restTemplate.postForObject(productServiceUrl, product, Product.class);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        try {
            restTemplate.delete(productServiceUrl + "/" + productId);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode());
        }
    }

    @Override
    public List<Recommendation> getRecommendations(Long id) {
        String url =  recommendationServiceUrl + "/product/" + id;

        logger.debug("Calling recommendation api on URL: {}", url);

        List<Recommendation> recommendations = restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {})
                .getBody();

        logger.debug("getRecommendations: {} for product with id: {}", recommendations, id);

        return recommendations;
    }

    @Override
    public Recommendation createRecommendation(Recommendation recommendation) {
        try {
            return restTemplate.postForObject(recommendationServiceUrl, recommendation, Recommendation.class);
        } catch(HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    public void deleteRecommendations(Long productId) {
        try {
            restTemplate.delete(recommendationServiceUrl + "?productId=" + productId);
        } catch(HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode());
        }
    }

    @Override
    public List<Review> getReviews(Long id) {
        String url = reviewServiceUrl + "/product/" + id;

        logger.debug("Calling reviews api on URL: {}", url);

        List<Review> reviews = restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {})
                .getBody();

        logger.debug("getReviews: {} for product with id: {}", reviews, id);

        return reviews;
    }

    @Override
    public Review createReview(Review review) {
        try {
            return restTemplate.postForObject(reviewServiceUrl, review, Review.class);
        }  catch(HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    public void deleteReviews(Long productId) {
        try {
            restTemplate.delete(reviewServiceUrl + "?productId=" + productId);
        }  catch(HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode());
        }
    }
}
