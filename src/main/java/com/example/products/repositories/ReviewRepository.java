package com.example.products.repositories;

import com.example.products.models.ProductReview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<ProductReview, String> {
    List<ProductReview> findByProductId(String productId);
}

