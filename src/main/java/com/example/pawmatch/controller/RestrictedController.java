package com.example.pawmatch.controller;

import com.example.pawmatch.dto.UpdateUserDTO;
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

}