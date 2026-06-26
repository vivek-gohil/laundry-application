package com.laundry.main.auth.entity;

import com.laundry.main.audit.BaseAuditableEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(
    name = "app_users",
    uniqueConstraints = {
      @UniqueConstraint(name = "uk_app_user_username", columnNames = "username"),
      @UniqueConstraint(name = "uk_app_user_email", columnNames = "email")
    })
public class AppUser extends BaseAuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "username", nullable = false, length = 50)
  private String username;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "first_name", nullable = false, length = 100)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Column(name = "mobile", nullable = false, length = 20)
  private String mobile;

  @Builder.Default
  @Column(name = "enabled", nullable = false)
  private Boolean enabled = true;

  @Builder.Default
  @Column(name = "account_non_locked", nullable = false)
  private Boolean accountNonLocked = true;

  @Builder.Default
  @Column(name = "account_non_expired", nullable = false)
  private Boolean accountNonExpired = true;

  @Builder.Default
  @Column(name = "credentials_non_expired", nullable = false)
  private Boolean credentialsNonExpired = true;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @Builder.Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
}
