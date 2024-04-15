package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.entity.GuardActivity;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardActivityRepository extends JpaRepository<GuardActivity, Long> {
    Long countByGuardAndStatus(Guard guard, GuardActivityStatus status);
    Page<GuardActivity> findAllBySubjectContainingIgnoreCase(String subject, Pageable pageable);
}
