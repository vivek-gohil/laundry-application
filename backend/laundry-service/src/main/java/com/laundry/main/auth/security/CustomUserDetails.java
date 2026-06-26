package com.laundry.main.auth.security;

import com.laundry.main.auth.entity.AppUser;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final AppUser appUser;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return appUser.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public String getPassword() {
    return appUser.getPassword();
  }

  @Override
  public String getUsername() {
    return appUser.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return appUser.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return appUser.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return appUser.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return appUser.isEnabled();
  }

  public AppUser getAppUser() {
    return appUser;
  }
}
