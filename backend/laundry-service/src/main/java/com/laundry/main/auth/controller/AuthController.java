package com.laundry.main.auth.controller;

import com.laundry.main.auth.dto.LoginRequest;
import com.laundry.main.auth.dto.LoginResponse;
import com.laundry.main.auth.service.AuthenticationService;
import com.laundry.main.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  @Operation(summary = "Login", description = "Authenticates a user and returns an access token")
  public ResponseEntity<ApiResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest request) {

    LoginResponse response = authenticationService.login(request);

    return ResponseEntity.ok(ApiResponse.success("Login successful.", response));
  }
}
