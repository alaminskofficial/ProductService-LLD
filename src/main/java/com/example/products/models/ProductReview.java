package com.example.products.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Getter
@Setter
@Document(collection = "reviews")
public class ProductReview {
    @Id
    private String id;
    private String productId;
    private String username;
    private int rating;
    private String comment;
    private String imageFileId; // ID of the file in GridFS
    private Map<String, Object> additionalDetails; // Extra fields
    private String timestamp;
}
