package com.laundry.main.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.laundry.main.auth.dto.LoginRequest;
import com.laundry.main.auth.dto.LoginResponse;
import com.laundry.main.auth.service.AuthenticationService;
import com.laundry.main.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authenticationService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful.",
                        response));
    }

}
