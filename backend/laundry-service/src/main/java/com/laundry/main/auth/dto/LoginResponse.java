package com.laundry.main.auth.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private String username;

    private String fullName;

    private Set<String> roles;
}
