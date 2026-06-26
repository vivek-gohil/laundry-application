package com.laundry.main.auth.dto;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Login response payload")
public class LoginResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType;

    @Schema(description = "Access token lifetime in seconds", example = "3600")
    private Long expiresIn;

    @Schema(description = "Authenticated username", example = "user@example.com")
    private String username;

    @Schema(description = "Authenticated user full name", example = "John Doe")
    private String fullName;

    @Schema(description = "Roles assigned to the user", example = "[\"USER\", \"ADMIN\"]")
    private Set<String> roles;
}
