package com.example.portalbackend.service.impl;

import com.example.portalbackend.config.security.dto.CustomUserDetails;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByDni(username).orElseThrow(EntityNotFoundException::new);
        return new CustomUserDetails(user);
    }
}
