package com.laundry.main.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request payload")
public class LoginRequest {

    @Schema(description = "Username of the user", example = "user@example.com")
    @NotBlank(message = "Username is required.")
    private String username;

    @Schema(description = "Password of the user", example = "Password123!")
    @NotBlank(message = "Password is required.")
    private String password;
}
