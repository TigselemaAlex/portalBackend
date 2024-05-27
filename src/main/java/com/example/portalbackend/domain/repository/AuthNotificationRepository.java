package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.AuthNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthNotificationRepository extends JpaRepository<AuthNotification, Long>{
    Optional<AuthNotification> findByUserId(Long userId);
    List<AuthNotification> findAllByDeviceTokenNotNull();
}
