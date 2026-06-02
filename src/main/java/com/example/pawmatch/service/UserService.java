package com.example.pawmatch.service;

import com.example.pawmatch.model.User;
import com.example.pawmatch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    UserRepository userRepository;  // Constructor injection is favoured over @Autowired injection

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findByEmailOrLastNameContaining(String email, String lastName) {
        return userRepository.findByEmailOrLastNameContaining(email, lastName);
    }

    @Override
    public List<User> findByEmailContaining(String email) {
        return userRepository.findByEmailContaining(email);
    }

    @Override
    public List<User> findByLastNameContaining(String lastName) {
        return userRepository.findByLastNameContaining(lastName);
    }
}
