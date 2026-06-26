package com.laundry.main.auth.config;

import com.laundry.main.auth.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http

        // Disable CSRF (REST API with stateless JWT authentication)
        // Safe because: tokens are explicitly sent in Authorization header, not auto-sent cookies
        .csrf(AbstractHttpConfigurer::disable)

        // Enable CORS
        .cors(Customizer.withDefaults())

        // Stateless Session
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Authorization Rules
        .authorizeHttpRequests(
            auth ->
                auth

                    // Authentication APIs
                    .requestMatchers("/api/auth/**")
                    .permitAll()

                    // Public APIs
                    .requestMatchers("/api/public/**")
                    .permitAll()

                    // Swagger
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                    .permitAll()

                    // OPTIONS request
                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()

                    // Everything else
                    .anyRequest()
                    .authenticated())

        // Authentication Provider
        .authenticationProvider(authenticationProvider)

        // JWT Filter
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
