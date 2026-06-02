package com.example.pawmatch.controller;

import com.example.pawmatch.dto.UserDTO;
import com.example.pawmatch.exception.ResourceNotFoundException;
import com.example.pawmatch.model.User;
import com.example.pawmatch.service.AuthService;
import com.example.pawmatch.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
//@CrossOrigin("*")   // Any client can communicate with this controller
public class AdminUserController {

    @Autowired
    UserService userService; // alternatively, use constructor to instantiate

    @Autowired
    AuthService authService;

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDTO signUpRequest){

        // save the user to the database
        return new ResponseEntity<>(authService.signUp(signUpRequest), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> allUsers() throws ResourceNotFoundException {

        // retrieve all users
        List<User> users = userService.findAll();

        // if no user is returned, throw custom exception ResourceNotFoundException
        if(users.isEmpty())
            throw new ResourceNotFoundException("No user found.");

        return new ResponseEntity<>(users, HttpStatus.OK); // 200

    }

    @PutMapping("/update/{id}") // Path variable
    public ResponseEntity<Object> updateUser(@PathVariable("id") Integer id, @Valid @RequestBody User user) throws ResourceNotFoundException{

        // find the user, else throw custom exception ResourceNotFoundException
        User currentUser = userService.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found."));

        return new ResponseEntity<>(authService.adminUpdateProfile(currentUser, user), HttpStatus.OK); // or use NO_CONTENT

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Integer id) throws ResourceNotFoundException {

        // get user by id and return the response, if no returned user, throw ResourceNotFoundException
        User user = userService.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found."));
        return new ResponseEntity<>(user, HttpStatus.OK); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") Integer id) throws ResourceNotFoundException{

        // delete the user only if the user is found, if no returned user, throw ResourceNotFoundException
        User user = userService.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found."));
        userService.deleteById(user.getId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Object> getUserByEmailOrLastName(
            @RequestParam("email") String email,                                            // email is a url param
            @RequestParam("lastName") String lastName) throws ResourceNotFoundException{    // lastName is a url param

        if(!email.isBlank() && !lastName.isBlank()) {                                   // email and lastName params found

            List<User> users = userService.
                    findByEmailOrLastNameContaining(email, lastName);

            if (users.isEmpty())
                throw new ResourceNotFoundException("User not found.");

            return new ResponseEntity<>(users, HttpStatus.OK);

        }else if(!email.isBlank() && lastName.isBlank()){                               // only email param found

            List<User> users = userService.findByEmailContaining(email);

            if (users.isEmpty())
                throw new ResourceNotFoundException("User not found.");

            return new ResponseEntity<>(users, HttpStatus.OK);

        }else if(email.isBlank() && !lastName.isBlank()){                               // only lastName param found

            List<User> users = userService.findByLastNameContaining(lastName);

            if (users.isEmpty())
                throw new ResourceNotFoundException("User not found.");

            return new ResponseEntity<>(users, HttpStatus.OK);
        }else{                                                                          // no params found, return everything
            return new ResponseEntity<>(allUsers(), HttpStatus.OK);
        }
    }

}
