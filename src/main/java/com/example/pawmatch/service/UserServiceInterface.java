package com.example.pawmatch.service;

import com.example.pawmatch.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    // Method signatures for UserService
    public User save(User user);

    public List<User> findAll();

    public void deleteById(Integer id);

    public Optional<User> findById(Integer id);

    public List<User> findByEmailOrLastNameContaining(String email, String lastName);

    public List<User> findByEmailContaining(String email);

    public List<User> findByLastNameContaining(String lastName);

}
