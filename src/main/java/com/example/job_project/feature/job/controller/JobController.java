package com.example.job_project.feature.job.controller;

import com.example.job_project.feature.job.model.Job;
import com.example.job_project.feature.job.service.JobServiceImpl;
import com.example.job_project.wrappers.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobServiceImpl jobService;

    public JobController(JobServiceImpl jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Job>>> findAllJobs() {
        List<Job> jobs = jobService.findAllJobs();
        return ResponseEntity.ok(new ApiResponse<>(true, "Jobs retrieved successfully", jobs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Job>> findJobById(@PathVariable Long id) {
        Job job = jobService.findJobById(id);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Job not found"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Job retrieved successfully", job));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createJob(@RequestBody Job job) {
        boolean isCreated = jobService.createJob(job);
        if (!isCreated) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to create job"));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Job created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Job>> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job updatedJob = jobService.updateJob(id, job);
        if (updatedJob == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Job not found"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Job updated successfully", updatedJob));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteJob(@PathVariable Long id) {
        boolean isDeleted = jobService.deleteJob(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Job not found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Job deleted successfully"));
    }
}
