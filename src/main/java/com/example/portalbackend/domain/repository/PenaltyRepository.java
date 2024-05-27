package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PenaltyRepository extends JpaRepository<Penalty, Long>, JpaSpecificationExecutor<Penalty> {

    Optional<Penalty> findFirstByOrderByIdDesc();
}
