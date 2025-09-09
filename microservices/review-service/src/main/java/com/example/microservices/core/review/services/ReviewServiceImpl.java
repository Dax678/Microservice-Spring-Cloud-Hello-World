package com.example.microservices.core.review.services;

import com.example.api.core.review.Review;
import com.example.api.core.review.ReviewService;
import com.example.api.exceptions.InvalidInputException;
import com.example.microservices.core.review.persistence.ReviewEntity;
import com.example.microservices.core.review.persistence.ReviewRepository;
import com.example.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewServiceImpl implements ReviewService {
    private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final ReviewMapper mapper;

    private final ServiceUtil serviceUtil;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper mapper, ServiceUtil serviceUtil) {
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Review> getReviews(Long productId) {
        if(productId < 1) throw new InvalidInputException("Product id must be greater than zero");

        List<ReviewEntity> entityList = reviewRepository.findAllByProductId(productId);
        List<Review> reviewList = mapper.entityListToApiList(entityList);

        reviewList.forEach(r -> r.setServiceAddress(serviceUtil.getServiceAddress()));

        return reviewList;
    }

    @Override
    public Review createReview(Review review) {
        try {
            ReviewEntity entity = mapper.apiToEntity(review);
            ReviewEntity newEntity = reviewRepository.save(entity);
            return mapper.entityToApi(newEntity);
        } catch (DuplicateKeyException e) {
            throw new InvalidInputException("Duplicate key, Review Id: " + review.getReviewId());
        }
    }

    @Override
    public void deleteReviews(Long productId) {
        reviewRepository.deleteAll(reviewRepository.findAllByProductId(productId));
    }
}
