package com.example.pawmatch.service;

import com.example.pawmatch.dto.UserDTO;
import com.example.pawmatch.dto.LoginDTO;
import com.example.pawmatch.dto.RefreshTokenDTO;
import com.example.pawmatch.dto.UpdateUserDTO;
import com.example.pawmatch.exception.RegistrationFailedException;
import com.example.pawmatch.exception.ResourceNotFoundException;
import com.example.pawmatch.model.User;
import com.example.pawmatch.repository.UserRepository;
import com.example.pawmatch.utils.JWTUtils;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Builder
@Service
public class AuthService {

    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // signUp method
    public UserDTO signUp(UserDTO signupRequest) throws RegistrationFailedException{

        User user = User.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .email(signupRequest.getEmail())
                .phone(signupRequest.getPhone())
                .address(signupRequest.getAddress())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();

        User result = userRepository.save(user);

        if(result.getId() > 0){

            // After the user has signed up, he/she can access the restricted pages
            var token = jwtUtils.generateToken(result);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), result);

            return UserDTO.builder()
                    .firstName(result.getFirstName())
                    .lastName(result.getLastName())
                    .email(result.getEmail())
                    .phone(result.getPhone())
                    .address(result.getAddress())
                    .token(token)
                    .refreshToken(refreshToken)
                    .message("User saved successfully.")
                    .build();
        }

        throw new RegistrationFailedException("Failed to register the user.");
    }

    // signIn method
    public UserDTO signIn(LoginDTO signInRequest) throws ResourceNotFoundException{

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("Invalid username or password."));

        var token = jwtUtils.generateToken(user);

        var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

        return UserDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .message("Signed in successfully.")
                .expirationTime("24Hr")
                .build();
    }

    // refreshToken
    public UserDTO refreshToken(RefreshTokenDTO refreshTokenRequest) throws ResourceNotFoundException{

        var refreshToken = refreshTokenRequest.getRefreshToken();

        String userEmail = jwtUtils.extractUsername(refreshToken);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid username or password."));

        if(jwtUtils.isTokenValid(refreshToken, user)){

            var newToken = jwtUtils.generateToken(user);
            var newRefreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            return UserDTO.builder()
                    .token(newToken)
                    .refreshToken(newRefreshToken)
                    .message("Refreshed token successfully.")
                    .expirationTime("168Hr")
                    .build();
        }

        return null;    // TODO
    }

    // returning userProfile - when the user views his/her profile
    public UserDTO profile(){

        // the bearer token is passed in and used by authentication
        // to extract the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var user = new User();
        user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .imageUrl(user.getImageUrl())
                .build();
    }

    // update userProfile
    public UserDTO updateProfile(UpdateUserDTO updateRequest){

        // the bearer token is passed in and used by authentication
        // to extract the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName()).map(_user->{
            _user.setFirstName(updateRequest.getFirstName());
            _user.setLastName(updateRequest.getLastName());
            _user.setPhone(updateRequest.getPhone());
            _user.setEmail(updateRequest.getEmail());
            _user.setAddress(updateRequest.getAddress());
            _user.setImageUrl(updateRequest.getImageUrl());


            if(updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty())
                _user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

            return userRepository.save(_user);
        }).orElseThrow();

        var token = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

        return UserDTO.builder()
                .message("Profile updated successfully.")
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .imageUrl(user.getImageUrl())
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    // update userProfile by admin
    public UserDTO adminUpdateProfile(User currentUser, UpdateUserDTO updateRequest) {

        User user = userRepository.findByEmail(currentUser.getEmail()).map(_user->{
            _user.setFirstName(updateRequest.getFirstName());
            _user.setLastName(updateRequest.getLastName());
            _user.setEmail(updateRequest.getEmail());
            _user.setPhone(updateRequest.getPhone());
            _user.setImageUrl(updateRequest.getImageUrl());

            if(updateRequest.getRole() != null)
                _user.setRole(updateRequest.getRole());

            if(updateRequest.getAddress() != null && !updateRequest.getAddress().isEmpty())
                _user.setAddress(updateRequest.getAddress());

            if(updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty())
                _user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

            return userRepository.save(_user);
        }).orElseThrow();

        return UserDTO.builder()
                .message("Profile updated successfully.")
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .imageUrl(user.getImageUrl())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }
}
