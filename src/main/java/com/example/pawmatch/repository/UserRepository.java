package com.example.pawmatch.repository;

import com.example.pawmatch.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository <User, Integer> {

    // Default capabilities given, eg:
    // save()
    // findById(Integer)
    // findAll()
    // count()
    // delete(User Object)
    // delete(Integer)

    // Derived queries
    // Using english terms to create queries to the database
    List<User> findByEmailOrLastNameContaining(String email, String lastName);

    List<User> findByEmailContaining(String email);

    List<User> findByLastNameContaining(String lastName);

    Optional<User> findByEmail(String username);
}
