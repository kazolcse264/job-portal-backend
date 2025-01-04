package com.example.job_project.feature.company.controller;

import com.example.job_project.feature.company.model.Company;
import com.example.job_project.feature.company.service.CompanyServiceImpl;
import com.example.job_project.wrappers.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    final CompanyServiceImpl companyService;

    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Company>>> findAllCompanies() {
        List<Company> companies = companyService.findAllCompanies();
        return ResponseEntity.ok(new ApiResponse<>(true, "Companies retrieved successfully", companies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> findCompanyById(@PathVariable Long id) {
        Company company = companyService.findCompanyById(id);
        if (company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Company not found"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Company retrieved successfully", company));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createCompany(@RequestBody Company company) {
        boolean isCreated = companyService.createCompany(company);
        if (!isCreated) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to create company"));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Company created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        Company updatedCompany = companyService.updateCompany(id, company);
        if (updatedCompany == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Company not found"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Company updated successfully", updatedCompany));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCompany(@PathVariable Long id) {
        boolean isDeleted = companyService.deleteCompany(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Company not found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Company deleted successfully"));
    }
}
