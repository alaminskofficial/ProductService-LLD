package com.example.products.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
public class ProductReviewRequest {
    private String productId;
    private String username;
    private int rating;
    private String comment;
    private Map<String, Object> additionalDetails; // Extra fields
    private String timestamp;
}
