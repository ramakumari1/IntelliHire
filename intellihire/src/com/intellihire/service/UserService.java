package com.intellihire.service;

import java.util.List;

import com.intellihire.entity.User;
import com.intellihire.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    private BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public void saveUser(User user) {

        if(repo.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        repo.save(user);
    }

    public Page<User> getUsersByPage(int page) {

        return repo.findAll(
                PageRequest.of(page,5)
        );
    }

    public User loginUser(String email,String password) {

        User user = repo.findByEmail(email);

        if(user != null &&
                encoder.matches(
                        password,
                        user.getPassword()
                )) {

            return user;
        }

        return null;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public void deleteUser(int id) {
        repo.deleteById(id);
    }

    public User getUserById(int id) {
        return repo.findById(id).orElse(null);
    }

    public void updateUser(User user) {

        User existingUser = repo.findById(user.getId()).orElse(null);

        if(existingUser != null){

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());

            if(user.getPassword() != null &&
                    !user.getPassword().trim().isEmpty()){

                existingUser.setPassword(
                        encoder.encode(user.getPassword())
                );
            }

            existingUser.setResume(user.getResume());

            repo.save(existingUser);
        }
    }

    public List<User> searchUsers(String name) {
        return repo.findByNameContaining(name);
    }

    public long totalUsers() {
        return repo.count();
    }
}