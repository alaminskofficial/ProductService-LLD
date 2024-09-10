package com.example.products.controllers;

import com.example.products.dtos.ProductReviewRequest;
import com.example.products.models.ProductReview;
import com.example.products.repositories.ReviewRepository;
import com.example.products.service.GridFSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products/{productId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GridFSService gridFSService;

    // POST endpoint to add a review with image upload
    @PostMapping
    public ResponseEntity<ProductReview> addReview(
            @PathVariable String productId,
            @RequestBody ProductReviewRequest reviewRequest,
            @RequestParam("file") MultipartFile file) throws IOException {

        ProductReview review = new ProductReview();
        review.setProductId(productId);
        review.setUsername(reviewRequest.getUsername());
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setAdditionalDetails(reviewRequest.getAdditionalDetails());

        // Store image in GridFS
        String imageFileId = gridFSService.storeFile(file);
        review.setImageFileId(imageFileId);
        review.setTimestamp(String.valueOf(System.currentTimeMillis()));

        // Save review to MongoDB
        ProductReview savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
    }

    // GET endpoint to retrieve reviews for a product
    @GetMapping
    public ResponseEntity<List<ProductReview>> getReviews(@PathVariable String productId) {
        List<ProductReview> reviews = reviewRepository.findByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    // GET endpoint to download the review image
    @GetMapping("/image/{fileId}")
    public ResponseEntity<InputStreamResource> getReviewImage(@PathVariable String fileId) throws IOException {
        InputStreamResource resource = gridFSService.getFile(fileId);
        return ResponseEntity.ok(resource);
    }

//    @GetMapping("/image/{imageFileId}")
//    public ResponseEntity<Resource> downloadImage(@PathVariable String imageFileId) throws IOException {
//        InputStream imageStream = gridFSService.getFile(imageFileId);
//        if (imageStream == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        InputStreamResource resource = new InputStreamResource(imageStream);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg"); // Adjust based on image type
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.jpg\""); // Adjust filename
//
//        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//    }
}


