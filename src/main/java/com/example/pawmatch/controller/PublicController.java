package com.example.pawmatch.controller;

import com.example.pawmatch.dto.LoginDTO;
import com.example.pawmatch.dto.RefreshTokenDTO;
import com.example.pawmatch.dto.UserDTO;
import com.example.pawmatch.exception.RegistrationFailedException;
import com.example.pawmatch.exception.ResourceNotFoundException;
import com.example.pawmatch.model.Pet;
import com.example.pawmatch.service.AuthService;
import com.example.pawmatch.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api")
// @CrossOrigin("*")   // // Any client can communicate with this controller
public class PublicController {

    @Autowired
    private PetService petService;

    @GetMapping("/all/pets")
    public ResponseEntity<Object> allPets() throws ResourceNotFoundException{

        // retrieve all pets
        List<Pet> pets = petService.findAll();

        // if no pet is returned, throw custom exception ResourceNotFoundException
        if(pets.isEmpty())
            throw new ResourceNotFoundException("Item not found.");

        return new ResponseEntity<>(pets, HttpStatus.OK); // 200
    }

    @GetMapping("/pet/{id}")
    public ResponseEntity<Object> getPetById(@PathVariable("id")Integer id) throws ResourceNotFoundException{

        // get pet by id and return the response, if no returned pet, throw ResourceNotFoundException
        Pet pet = petService.findById(id).orElseThrow(()->new ResourceNotFoundException("Item not found."));
        return new ResponseEntity<>(pet, HttpStatus.OK); // 200
    }

}