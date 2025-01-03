package com.example.job_project.company.repository;

import com.example.job_project.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepositoy extends JpaRepository<Company,Long> {
}
