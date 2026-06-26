package com.laundry.main.auth.repository;

import com.laundry.main.auth.entity.AppUser;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  @EntityGraph(attributePaths = "roles")
  Optional<AppUser> findByUsername(String username);

  Optional<AppUser> findByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
