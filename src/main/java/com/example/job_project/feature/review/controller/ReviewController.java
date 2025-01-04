package com.example.job_project.feature.review.controller;

import com.example.job_project.feature.review.model.Review;
import com.example.job_project.feature.review.service.ReviewServiceImpl;
import com.example.job_project.wrappers.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/companies/{companyId}")
public class ReviewController {
    final ReviewServiceImpl reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("reviews")
    public ResponseEntity<ApiResponse<List<Review>>> findAllReviews(@PathVariable Long companyId) {
        List<Review> reviews = reviewService.findAllReviews(companyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Companies retrieved successfully", reviews));
    }

    @GetMapping("reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Review>> findCompanyById(@PathVariable Long companyId,
                                                               @PathVariable Long reviewId) {
        Review review = reviewService.findReviewById(companyId, reviewId);
        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Review not found"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Review retrieved successfully", review));
    }

    @PostMapping("reviews")
    public ResponseEntity<ApiResponse<String>> createReview(@PathVariable Long companyId, @RequestBody Review review) {
        boolean isCreated = reviewService.createReview(companyId, review);
        if (!isCreated) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to create review"));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Review created successfully"));
    }

    @PutMapping("reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Review>> updateReview(@PathVariable Long companyId,
                                                            @PathVariable Long reviewId,
                                                            @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(companyId, reviewId, review);
        if (updatedReview == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Review not found"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Review updated successfully", updatedReview));
    }

    @DeleteMapping("reviews/{reviewId}")
    public ResponseEntity<ApiResponse<String>> deleteReview(@PathVariable Long companyId,
                                                            @PathVariable Long reviewId) {
        boolean isDeleted = reviewService.deleteReview(companyId, reviewId);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Review not found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Review deleted successfully"));
    }

}
