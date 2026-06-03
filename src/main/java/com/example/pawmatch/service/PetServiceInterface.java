package com.example.pawmatch.service;

import com.example.pawmatch.model.Pet;
import com.example.pawmatch.model.User;

import java.util.List;
import java.util.Optional;

public interface PetServiceInterface {

    // Method signatures for PetService
    public Pet save(Pet pet);

    public List<Pet> findAll();

    public void deleteById(Integer id);

    public Optional<Pet> findById(Integer id);

}
