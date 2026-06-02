package com.example.pawmatch.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {

    private final SecretKey secretKey;

    public static final long EXPIRATION_TIME = 86400000;                // 24 hours == 86400000 milliseconds
    private static final long REFRESH_EXPIRATION_TIME = 604800000;      // 7 Days == 604800000 milliseconds


    // Inject the secretString from the application properties when the constructor of class has been invoked, and to
    // generate a message authentication code (MAC) using the secret key in combination with the SHA-256 hash function.
    public JWTUtils(@Value("${jwt.secret}") String secretString) {

        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }


    // takes in the user's details to generate the JWT token with an expiration duration of 24 hours
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("roles", userDetails.getAuthorities())
                .signWith(secretKey)
                .compact();
    }

    // takes in the claims (aka payload, e.g. expirationTime) and the user's details to generate a refresh token
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    // check if the token used is valid
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // returns the username (email) using the passed-in token
    // the token is the payload containing user information (e.g. email, expirationTime)
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    // this generic method, represented by <T> returns a generic type as well T
    // returns the claims (payload) from a JWT (JSON Web Token)
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }

    // returns whether the token is expired by comparing the token's expiration against the current date
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

}