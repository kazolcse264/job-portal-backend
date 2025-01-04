package com.example.job_project.feature.review.service;

import com.example.job_project.exceptions.ResourceNotFoundException;
import com.example.job_project.exceptions.ServiceException;
import com.example.job_project.feature.company.model.Company;
import com.example.job_project.feature.company.service.CompanyService;
import com.example.job_project.feature.review.model.Review;
import com.example.job_project.feature.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> findAllReviews(Long companyId) {
        try {
            return reviewRepository.findByCompanyId(companyId);
        } catch (Exception e) {
            logAndThrow("Error fetching reviews for company ID: " + companyId, e);
            return Collections.emptyList();
        }
    }

    @Override
    public Review findReviewById(Long companyId, Long reviewId) {
        try {
            validateCompanyExists(companyId);
            return reviewRepository.findById(reviewId)
                    .filter(review -> review.getCompany().getId().equals(companyId))
                    .orElseThrow(() -> new ResourceNotFoundException("Review not found for ID: " + reviewId + " under company ID: " + companyId));
        } catch (Exception e) {
            logAndThrow("Error fetching review by ID: " + reviewId + " for company ID: " + companyId, e);
            return null;
        }
    }

    @Override
    public boolean createReview(Long companyId, Review review) {
        try {
            Company company = validateCompanyExists(companyId);
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        } catch (Exception e) {
            logAndThrow("Error creating review for company ID: " + companyId, e);
            return false;
        }
    }

    @Override
    public Review updateReview(Long companyId, Long reviewId, Review review) {
        try {
            Company company = validateCompanyExists(companyId);
            Review existingReview = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ResourceNotFoundException("Review not found for ID: " + reviewId));

            // Update fields
            existingReview.setTitle(review.getTitle());
            existingReview.setDescription(review.getDescription());
            existingReview.setRating(review.getRating());
            existingReview.setCompany(company);

            return  reviewRepository.save(existingReview);
        } catch (Exception e) {
            logAndThrow("Error updating review ID: " + reviewId + " for company ID: " + companyId, e);
            return null;
        }
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        try {
            validateCompanyExists(companyId);
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ResourceNotFoundException("Review not found for ID: " + reviewId));

            // Disassociate review from company and delete
            review.getCompany().getReviews().remove(review);
            review.setCompany(null);
            reviewRepository.delete(review);
            return true;
        } catch (Exception e) {
            logAndThrow("Error deleting review ID: " + reviewId + " for company ID: " + companyId, e);
            return false;
        }
    }

    private Company validateCompanyExists(Long companyId) {
        return companyService.findCompanyById(companyId);
    }

    private void logAndThrow(String message, Exception e) {
        System.err.println(message); // Replace with logger.error(message, e);
        throw new ServiceException(message, e);
    }
}

