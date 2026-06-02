package com.example.pawmatch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties                       // ignore any JSON properties that are not bound to any fields during serialisation
@JsonInclude(JsonInclude.Include.NON_NULL)  // ignored fields that are empty or null during serialization
public class LoginDTO {

    @NotBlank(message = "Email must not be empty.")
    @Email(regexp = "^(?!\\.)(?!.*\\.{2})[a-zA-Z0-9._%+-]+(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email is not valid."
    )
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Password cannot be blank.")
    // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Please use a strong password.")
    private String password;

}