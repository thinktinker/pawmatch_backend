package com.example.pawmatch.service;

import com.example.pawmatch.model.Application;
import com.example.pawmatch.model.Pet;
import com.example.pawmatch.repository.ApplicationRepository;
import com.example.pawmatch.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService implements ApplicationServiceInterface {

    ApplicationRepository applicationRepository;  // Constructor injection is favoured over @Autowired injection


    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        applicationRepository.deleteById(id);
    }

    @Override
    public Optional<Application> findById(Integer id) {
        return applicationRepository.findById(id);
    }

    public List<Application> findByPetId(Integer id){
        return applicationRepository.findByPetId(id);
    }
}
