package com.example.pawmatch.exception;

public class ResourceNotFoundException extends Throwable{
    public ResourceNotFoundException(String message){
        super(String.format("%s Please try again.", message));
    }
}
