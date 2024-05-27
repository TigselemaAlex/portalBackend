package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.PenaltyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyTypeRepository extends JpaRepository<PenaltyType, Long>{
    List<PenaltyType> findAllByActiveIsTrueOrderById();
    List<PenaltyType> findAllByOrderById();
}
