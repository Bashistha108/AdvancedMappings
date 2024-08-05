package com.ende.FinalAdvancedMappings.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private int id;  // The student's ID
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor
    public CustomUserDetails(int id, String username, String password, 
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private boolean hasRole(String role) {
        return authorities.stream()
                          .anyMatch(authority -> authority.getAuthority().equals(role));
    }

    // Check if the user is a student
    public boolean isStudent() {
        return hasRole("ROLE_STUDENT") || hasRole("ROLE_VERTRETER");
    }

    // Check if the user is a professor or dean
    public boolean isProfessor() {
        return hasRole("ROLE_PROFESSOR") || hasRole("ROLE_DEAN")|| hasRole("ROLE_CS")|| hasRole("ROLE_CHEMISTRY")|| hasRole("ROLE_PHYSICS")|| hasRole("ROLE_MATH");
    }
}
