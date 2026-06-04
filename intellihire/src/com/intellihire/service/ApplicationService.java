package com.intellihire.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellihire.entity.Application;
import com.intellihire.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repo;

    public void save(Application app) {
        repo.save(app);
    }

    public List<Application> getAllApplications() {
        return repo.findAll();
    }
    public long totalApplications() {
        return repo.count();
    }
    public Application getById(int id){
        return repo.findById(id).orElse(null);
    }
    public List<Application> getApplicationsByUser(
            String email){

        return repo.findByUserEmail(email);
    }
    public long countApplicationsByJob(String jobTitle){
        return repo.countByJobTitle(jobTitle);
    }


    public boolean alreadyApplied(
            String email,
            String job) {

        return repo.existsByUserEmailAndJobTitle(
                email,
                job
        );
    }
}