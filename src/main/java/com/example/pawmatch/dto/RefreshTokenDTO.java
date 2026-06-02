package com.example.pawmatch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties                       // ignore any JSON properties that are not bound to any fields during serialisation
@JsonInclude(JsonInclude.Include.NON_NULL)  // ignored fields that are empty or null during serialization
public class RefreshTokenDTO {

    @NotBlank(message = "Refresh token cannot be empty.")
    String refreshToken;

}