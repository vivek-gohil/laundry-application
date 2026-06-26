package com.laundry.main.auth.repository;

import com.laundry.main.auth.entity.Role;
import com.laundry.main.auth.enums.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByRoleName(RoleName roleName);

  boolean existsByRoleName(RoleName roleName);
}
