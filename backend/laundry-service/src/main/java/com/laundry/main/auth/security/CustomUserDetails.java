package com.laundry.main.auth.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.laundry.main.auth.entity.AppUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails
        implements UserDetails {

    private final AppUser appUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return appUser.getRoles()
                .stream()
                .map(role ->
                        new SimpleGrantedAuthority(
                                role.getRoleName().name()))
                .collect(Collectors.toSet());
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
        return appUser.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return appUser.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return appUser.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return appUser.getEnabled();
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
