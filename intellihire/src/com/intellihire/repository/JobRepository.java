package com.intellihire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intellihire.entity.Job;
import java.util.List;


public interface JobRepository
        extends JpaRepository<Job,Integer> {

    List<Job> findByTitleContainingIgnoreCase(String title);
}