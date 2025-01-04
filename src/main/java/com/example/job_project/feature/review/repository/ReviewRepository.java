package com.example.job_project.feature.review.repository;

import com.example.job_project.feature.company.model.Company;
import com.example.job_project.feature.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCompanyId(Long companyId);

    Company findCompanyByCompanyId(Long companyId);
}
