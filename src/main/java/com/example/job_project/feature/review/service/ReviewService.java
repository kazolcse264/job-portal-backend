package com.example.job_project.feature.review.service;

import com.example.job_project.feature.review.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviews(Long companyId);

    Review findReviewById(Long companyId, Long reviewId);

    boolean createReview(Long companyId, Review review);

    Review updateReview(Long companyId, Long reviewId, Review review);

    boolean deleteReview(Long companyId, Long reviewId);

}
