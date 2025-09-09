package com.example.microservices.core.review.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {
    @Transactional(readOnly = true)
    List<ReviewEntity> findAllByProductId(Long productId);
}
