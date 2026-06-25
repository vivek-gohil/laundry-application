package com.laundry.main.auth.service.impl;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laundry.main.auth.dto.LoginRequest;
import com.laundry.main.auth.dto.LoginResponse;
import com.laundry.main.auth.entity.AppUser;
import com.laundry.main.auth.jwt.JwtService;
import com.laundry.main.auth.repository.AppUserRepository;
import com.laundry.main.auth.service.AuthenticationService;
import com.laundry.main.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        AppUser appUser = appUserRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid username or password."));

        appUser.setLastLogin(LocalDateTime.now());

        String token = jwtService.generateToken(appUser);

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .username(appUser.getUsername())
                .fullName(appUser.getFirstName() + " " + appUser.getLastName())
                .roles(
                        appUser.getRoles()
                                .stream()
                                .map(role -> role.getRoleName().name())
                                .collect(Collectors.toSet()))
                .build();
    }
}
