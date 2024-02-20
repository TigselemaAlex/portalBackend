package com.example.portalbackend.api.dto.response.user;

import com.example.portalbackend.api.dto.response.role.RoleResponse;
import com.example.portalbackend.domain.entity.User;

import java.time.Instant;
import java.util.List;

public record UserResponse(
        Long id,
        String names,
        String surnames,
        String email,
        String dni,
        Boolean active,
        String phone,
        Instant createdAt,
        Instant updatedAt,
        List<RoleResponse> roles
) {

    public UserResponse(User user) {
        this(
                user.getId(),
                user.getNames(),
                user.getSurnames(),
                user.getEmail(),
                user.getDni(),
                user.getActive(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getAuthRoles().stream().map(
                        authRole -> new RoleResponse(authRole.getRole())
                ).toList()
        );
    }
}
