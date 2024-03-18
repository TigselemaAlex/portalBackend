package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.SocialEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;

public interface SocialEventRepository extends JpaRepository<SocialEvent, Long> {
    Page<SocialEvent> findAllByDateBetween(Calendar from, Calendar to, Pageable pageable);
}
