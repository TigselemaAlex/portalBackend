package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.AuthRole;
import com.example.portalbackend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<AuthRole, Long> {
    void deleteAllByUser(User userToUpdate);
}
