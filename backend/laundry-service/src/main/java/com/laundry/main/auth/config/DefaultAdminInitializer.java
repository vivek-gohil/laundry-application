package com.laundry.main.auth.config;

import com.laundry.main.auth.entity.AppUser;
import com.laundry.main.auth.entity.Role;
import com.laundry.main.auth.enums.RoleName;
import com.laundry.main.auth.repository.AppUserRepository;
import com.laundry.main.auth.repository.RoleRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DefaultAdminInitializer {

  private final AppUserRepository appUserRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Bean
  CommandLineRunner initializeAdmin() {
    return args -> {
      if (appUserRepository.existsByUsername("admin")) {
        return;
      }

      Role adminRole =
          roleRepository
              .findByRoleName(RoleName.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

      AppUser admin =
          AppUser.builder()
              .username("admin")
              .password(passwordEncoder.encode("Admin@123"))
              .firstName("System")
              .lastName("Administrator")
              .email("admin@laundry.com")
              .mobile("9999999999")
              .enabled(true)
              .accountNonExpired(true)
              .accountNonLocked(true)
              .credentialsNonExpired(true)
              .roles(Set.of(adminRole))
              .build();

      appUserRepository.save(admin);
      System.out.println("Default Admin User Created.");
    };
  }
}
