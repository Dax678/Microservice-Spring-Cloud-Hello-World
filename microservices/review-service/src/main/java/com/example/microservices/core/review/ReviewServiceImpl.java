package com.example.microservices.core.review;

import com.example.api.core.review.Review;
import com.example.api.core.review.ReviewService;
import com.example.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewServiceImpl implements ReviewService {
    private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Override
    public List<Review> getReviews(Long id) {
        logger.debug("getReview called - productId={}", id);
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(1L, "Review Author", "Review Subject1", "addr"));
        reviews.add(new Review(2L, "Review Author", "Review Subject1", "addr"));
        reviews.add(new Review(3L, "Review Author", "Review Subject1", "addr"));

        return reviews;
    }
}
