package com.example.job_project.feature.company.repository;

import com.example.job_project.feature.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepositoy extends JpaRepository<Company,Long> {
}
