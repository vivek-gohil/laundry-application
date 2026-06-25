package com.laundry.main.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.laundry.main.auth.entity.Role;
import com.laundry.main.auth.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName roleName);

    boolean existsByRoleName(RoleName roleName);
}
