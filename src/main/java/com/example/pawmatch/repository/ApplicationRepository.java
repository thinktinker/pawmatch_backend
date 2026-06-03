package com.example.pawmatch.repository;

import com.example.pawmatch.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository <Application, Integer> {

    // Derived: find user by id
    List<Application> findByUserId(Integer id);

    List<Application> findByPetId(Integer id);
}
