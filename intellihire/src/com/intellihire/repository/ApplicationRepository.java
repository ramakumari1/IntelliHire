package com.intellihire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intellihire.entity.Application;


public interface ApplicationRepository
        extends JpaRepository<Application,Integer> {

    List<Application> findByUserEmail(String userEmail);
    boolean existsByUserEmailAndJobTitle(
            String userEmail,
            String jobTitle
    );
    long countByJobTitle(String jobTitle);


}