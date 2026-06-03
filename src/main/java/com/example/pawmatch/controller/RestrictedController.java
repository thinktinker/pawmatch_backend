package com.example.pawmatch.controller;

import com.example.pawmatch.dto.ApplicationDTO;
import com.example.pawmatch.dto.UpdateUserDTO;
import com.example.pawmatch.exception.ResourceNotFoundException;
import com.example.pawmatch.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restricted")
// @CrossOrigin("*")   // Any client can communicate with this controller
public class RestrictedController {

    @Autowired
    private AuthService authService;

    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile(){
        return new ResponseEntity<>(authService.profile(), HttpStatus.OK);
    }

    @PutMapping("/updateprofile")
    public ResponseEntity<Object> updateProfile(@Valid @RequestBody UpdateUserDTO updateProfileRequest){
        return new ResponseEntity<>(authService.updateProfile(updateProfileRequest), HttpStatus.OK);
    }

    // Path variable id refers to the pet's id
    @PostMapping("/apply/{id}")
    public ResponseEntity<Object> adoptionApplication(@PathVariable("id") Integer pet_id,  @Valid @RequestBody ApplicationDTO applicationRequest) throws ResourceNotFoundException {
        return new ResponseEntity<>(authService.adoptionApplication(pet_id, applicationRequest), HttpStatus.OK);
    }

    // Path variable id refers to the user's id
    @GetMapping("/adoptionlist")
    public ResponseEntity<Object> adoptionList() throws ResourceNotFoundException {
        return new ResponseEntity<>(authService.adoptionList(), HttpStatus.OK);
    }

}