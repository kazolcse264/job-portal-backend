package com.example.job_project.job.service;

import com.example.job_project.exceptions.ResourceNotFoundException;
import com.example.job_project.exceptions.ServiceException;
import com.example.job_project.job.model.Job;
import com.example.job_project.job.repository.JobRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public boolean createJob(Job job) {
        if (job == null) {
            throw new IllegalArgumentException("Job cannot be null.");
        }
        try {
            jobRepository.save(job);
            return true;
        } catch (Exception e) {
            // Log the exception and throw a custom exception
            throw new ServiceException("Failed to create job", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Job findJobById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Job ID cannot be null.");
        }
        Optional<Job> optionalJob = jobRepository.findById(id);
        return optionalJob.orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));
    }

    @Override
    public Job updateJob(Long id, Job job) {
        if (id == null || job == null) {
            throw new IllegalArgumentException("Job ID and Job cannot be null.");
        }

        try {
            Job existingJob = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));

            existingJob.setTitle(job.getTitle());
            existingJob.setDescription(job.getDescription());
            existingJob.setMaxSalary(job.getMaxSalary());
            existingJob.setMinSalary(job.getMinSalary());
            existingJob.setLocation(job.getLocation());

            return jobRepository.save(existingJob);
        } catch (Exception e) {
            // Log the exception and throw a custom exception
            throw new ServiceException("Failed to update job", e);
        }
    }

    @Override
    public boolean deleteJob(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Job ID cannot be null.");
        }
        try {
            if (!jobRepository.existsById(id)) {
                throw new ResourceNotFoundException("Job not found with ID: " + id);
            }
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            // Log the exception and throw a custom exception
            throw new ServiceException("Failed to delete job", e);
        }
    }
}

