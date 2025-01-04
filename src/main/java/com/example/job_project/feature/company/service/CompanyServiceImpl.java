package com.example.job_project.feature.company.service;

import com.example.job_project.feature.company.model.Company;
import com.example.job_project.feature.company.repository.CompanyRepositoy;
import com.example.job_project.exceptions.ResourceNotFoundException;
import com.example.job_project.exceptions.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepositoy companyRepositoy;

    public CompanyServiceImpl(CompanyRepositoy companyRepositoy) {
        this.companyRepositoy = companyRepositoy;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> findAllCompanies() {
        return companyRepositoy.findAll();
    }

    @Override
    public boolean createCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }
        try {
            companyRepositoy.save(company);
            return true;
        } catch (Exception e) {
            // Log the exception and throw a custom exception
            throw new ServiceException("Failed to create company", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Company findCompanyById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Company ID cannot be null.");
        }
        Optional<Company> optionalCompany = companyRepositoy.findById(id);
        return optionalCompany.orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        if (id == null || company == null) {
            throw new IllegalArgumentException("Company ID and Company cannot be null.");
        }

        try {
            Company existingCompany = companyRepositoy.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

            existingCompany.setName(company.getName());
            existingCompany.setDescription(company.getDescription());
            existingCompany.setJobs(company.getJobs());

            return companyRepositoy.save(existingCompany);
        } catch (Exception e) {
            // Log the exception and throw a custom exception
            throw new ServiceException("Failed to update company", e);
        }
    }

    @Override
    public boolean deleteCompany(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Company ID cannot be null.");
        }
        try {
            if (!companyRepositoy.existsById(id)) {
                throw new ResourceNotFoundException("Company not found with ID: " + id);
            }
            Company company = companyRepositoy.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

            // Detach or delete associated entities
            company.getJobs().forEach(job -> job.setCompany(null));
            company.getReviews().forEach(review -> review.setCompany(null));

            // Save changes (if necessary)
            companyRepositoy.save(company);

            // Delete the company
            companyRepositoy.delete(company);
            companyRepositoy.deleteById(id);
            return true;
        } catch (Exception e) {
            // Log the exception and throw a custom exception
            throw new ServiceException("Failed to delete company", e);
        }
    }
}
