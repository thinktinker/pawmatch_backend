package com.example.pawmatch.repository;

import com.example.pawmatch.model.Application;
import com.example.pawmatch.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository <Pet, Integer> {

}
