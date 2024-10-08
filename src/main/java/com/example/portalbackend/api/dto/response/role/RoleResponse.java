package com.example.portalbackend.api.dto.response.role;

import com.example.portalbackend.domain.entity.Role;

import java.time.Instant;

public record RoleResponse(
        Long id,
        String name,
        String description,
        Boolean active,
        Instant createdAt,
        Instant updatedAt
) {
    public RoleResponse(Role role){
        this(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getActive(),
                role.getCreatedAt(),
                role.getUpdatedAt()
        );
    }
}
