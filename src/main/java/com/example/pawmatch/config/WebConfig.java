package com.example.pawmatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String originPort = "5500";

    private static final String originHost = "127.0.0.1";

    private String strUrl = "http://";

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        String mainUrl= strUrl.concat(originHost).concat(":").concat(originPort);

        // Allow CORS for /auth/**
        registry.addMapping("/auth/**")                          // Set the pathPattern
                .allowedOrigins(mainUrl)                                    // Restricted requests from: "http://127.0.0.1:5500"
                .allowedMethods("GET", "POST")                              // Allowable HTTP methods
                .allowCredentials(false)                                    // Credentials aren't typically needed for public API
                .allowedHeaders(
                        "Content-Type",                                     // Media type of request body (e.g. application/json)
                        "Accept",                                           // Expected response format (e.g. application/json)
                        "X-Requested-With",                                 // Identify AJAX requests (optional, common in web apps)
                        "Cache-Control",                                    // Controls cache behavior (e.g. no-cache)
                        "Origin",                                           // Sent by browser enforce CORS policies
                        "User-Agent")                                       // Identify sender (optional, for logging or analytics)
                .maxAge(3600);                                              // Set max age (sec) for CORS response cached by browser

        // Allow CORS for /restricted/**
        registry.addMapping("/restricted/**")                    // Set the pathPattern
                .allowedOrigins(mainUrl)                                    // Restricted requests from: "http://127.0.0.1:5500"
                .allowedMethods("GET", "POST", "PUT")                       // Allowable HTTP methods
                .allowCredentials(true)                                     // Allow credentials: cookies, auth headers
                .allowedHeaders(
                        "Authorization",                                    // Pass in the auth credentials (e.g. JWT, API key)
                        "Content-Type",                                     // Media type of request body (e.g. application/json)
                        "Accept",                                           // Expected response format (e.g. application/json)
                        "X-Requested-With",                                 // Identify AJAX requests (optional, common in web apps)
                        "Cache-Control",                                    // Controls cache behavior (e.g. no-cache)
                        "X-CSRF-Token",                                     // IMPORTANT: Token passed to validate against CSRF
                        "Origin",                                           // Sent by browser enforce CORS policies
                        "User-Agent")                                       // Identify sender (optional, for logging or analytics)
                .maxAge(3600);                                              // Set max age (sec) for CORS response cached by browser

    }
}

