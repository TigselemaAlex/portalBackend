package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.OutcomeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutcomeTypeRepository extends JpaRepository<OutcomeType, Long> {

    List<OutcomeType> findAllByOrderById();

    List<OutcomeType> findAllByActiveIsTrue();
}
