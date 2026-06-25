package com.laundry.main.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.laundry.main.auth.entity.Role;
import com.laundry.main.auth.enums.RoleName;
import com.laundry.main.auth.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityDataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initializeRoles() {
        return args -> {
            createRoleIfNotExists(RoleName.ROLE_ADMIN, "System Administrator");
            createRoleIfNotExists(RoleName.ROLE_MANAGER, "Store Manager");
            createRoleIfNotExists(RoleName.ROLE_OPERATOR, "Laundry Operator");
        };
    }

    private void createRoleIfNotExists(RoleName roleName, String description) {
        if (!roleRepository.existsByRoleName(roleName)) {
            Role role = Role.builder()
                    .roleName(roleName)
                    .description(description)
                    .build();
            roleRepository.save(role);
        }
    }
}
