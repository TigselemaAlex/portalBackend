package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
}
