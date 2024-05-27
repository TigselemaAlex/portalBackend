package com.example.portalbackend.api.dto.response.jwt;

import com.example.portalbackend.config.security.dto.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record JwtResponse(
        String token,
        String fullName,
        Long id,
        Collection<? extends GrantedAuthority> authorities

) {
    public JwtResponse(CustomUserDetails userDetails, String token){
        this(token, userDetails.getSurnames() + " " +userDetails.getNames(), userDetails.getId(), userDetails.getAuthorities());
    }
}
