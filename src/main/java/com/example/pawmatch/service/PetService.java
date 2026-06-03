package com.example.pawmatch.service;

import com.example.pawmatch.model.Pet;
import com.example.pawmatch.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService implements PetServiceInterface {

    PetRepository petRepository;  // Constructor injection is favoured over @Autowired injection


    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        petRepository.deleteById(id);
    }

    @Override
    public Optional<Pet> findById(Integer id) {
        return petRepository.findById(id);
    }
}
