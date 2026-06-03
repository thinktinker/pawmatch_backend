package com.example.pawmatch.service;

import com.example.pawmatch.model.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationServiceInterface {

    // Method signatures for PetService
    public Application save(Application pet);

    public List<Application> findAll();

    public void deleteById(Integer id);

    public Optional<Application> findById(Integer id);

}
