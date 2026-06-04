package com.intellihire.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellihire.entity.Job;
import com.intellihire.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository repo;

    public void saveJob(Job job) {
        repo.save(job);
    }
    public Job getJobById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Job> getAllJobs() {
        return repo.findAll();
    }
    public List<Job> searchJobs(String title){
        return repo.findByTitleContainingIgnoreCase(title);
    }

    public void deleteJob(int id) {
        repo.deleteById(id);
    }
    public long totalJobs() {
        return repo.count();
    }
}