package com.example.job_project.job.service;

import com.example.job_project.job.model.Job;

import java.util.List;

public interface JobService {
    List<Job> findAllJobs();

    boolean createJob(Job job);

    Job findJobById(Long id);

    Job updateJob(Long id, Job job);

    boolean deleteJob(Long id);
}