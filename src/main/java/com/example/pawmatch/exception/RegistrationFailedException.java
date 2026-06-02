package com.example.pawmatch.exception;

public class RegistrationFailedException extends RuntimeException{
    public RegistrationFailedException(String message){
        super(String.format("%s Please try again.", message));
    }
}
