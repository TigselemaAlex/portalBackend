package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
