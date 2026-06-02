package com.example.pawmatch.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)  // when an exception occurs, the GlobalExceptionHandler takes highest precedence
@ControllerAdvice                   // addresses exceptions across the entire app (globally)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 1. Address Entity Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> httpEntityNotFound(ResourceNotFoundException ex){

        // store our response as a HashMap
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 2. Address message sent over that is not readable (e.g. Add / Update User)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Use the custom exception, called MessageNotReadableException
        MessageNotReadableException messageNotReadableException = new MessageNotReadableException();

        // store our response as a HashMap
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", messageNotReadableException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 3. Address specific inputs that are NOT valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err->{ // we loop through the various validation errors
            String field = ((FieldError) err).getField();   // "firstName"
            String errMessage = err.getDefaultMessage();    // "Min. 2 characters in first name"
            errors.put(field, errMessage);
        });

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 4. Address RegistrationFailedException
    @ExceptionHandler(RegistrationFailedException.class)
    protected ResponseEntity<Object> httpEntityRegistrationFailed(RegistrationFailedException ex){

        // store our response as a HashMap
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 5. Catch-all global exception handler to prevent unhandled leakage (e.g. security filters)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {

        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", ex.getMessage()); // ex.getMessage()

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 6. Catch exception triggered by authenticationManager.authenticate()
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationFailure(AuthenticationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Bad credentials. Please check your email and password.");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
    }

    // 7. Catch exception triggered by a malformed or tampered token
    @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
    protected ResponseEntity<Object> handleJwtSignatureException(JwtException ex) {

        Map<String, String> errorResponse = new HashMap<>();

        // Security best practice: Don't leak raw library exceptions to the client.
        // Give them a clean, clear message.
        errorResponse.put("error", "JWT token is invalid, tampered with, or improperly signed.");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
    }

}
