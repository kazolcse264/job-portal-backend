package com.example.job_project.feature.company.service;

import com.example.job_project.feature.company.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> findAllCompanies();

    Company findCompanyById(Long id);

    boolean createCompany(Company company);

    Company updateCompany(Long id, Company updatedCompany);

    boolean deleteCompany(Long id);
}
