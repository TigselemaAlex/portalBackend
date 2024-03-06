package com.example.portalbackend.config.security.dto;

import com.example.portalbackend.domain.entity.AuthRole;
import com.example.portalbackend.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User implements UserDetails {

    private String username;
    private String password;

    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.setId(user.getId());
        this.setNames(user.getNames());
        this.setSurnames(user.getSurnames());
        this.username = user.getDni();
        this.password = user.getPassword();

        List<GrantedAuthority> auths = new ArrayList<>();
        for (AuthRole role : user.getAuthRoles()) {
            auths.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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
}
