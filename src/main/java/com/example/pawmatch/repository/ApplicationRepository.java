package com.example.pawmatch.repository;

import com.example.pawmatch.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository <Application, Integer> {
}
