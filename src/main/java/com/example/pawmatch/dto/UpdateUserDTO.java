package com.example.pawmatch.dto;

import com.example.pawmatch.model.EnumExperienceLevel;
import com.example.pawmatch.model.EnumHousingType;
import com.example.pawmatch.model.User;
import com.example.pawmatch.model.EnumRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties                       // ignore any JSON properties that are not bound to any fields during serialisation
@JsonInclude(JsonInclude.Include.NON_NULL)  // ignored fields that are empty or null during serialization
public class UpdateUserDTO {

    @NotBlank(message = "First name must not be empty.")
    @Size(min = 2, message = "Minimum 2 characters for first name.")
    private String firstName;

    @NotBlank(message = "Last name must not be empty.")
    @Size(min = 2, message = "Minimum 2 characters for last name.")
    private String lastName;

    @NotBlank(message = "Email must not be empty.")
    @Email(regexp = "^(?!\\.)(?!.*\\.{2})[a-zA-Z0-9._%+-]+(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email is not valid."
    )
    private String email;

    // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Please use a strong password.")
    private String password;

    private String phone;
    private String address;
    private EnumHousingType housingType;
    private EnumExperienceLevel experienceLevel;
    private String imageUrl;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private EnumRole role;
    private String message;
}