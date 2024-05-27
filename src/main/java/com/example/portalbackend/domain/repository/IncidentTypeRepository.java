package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentTypeRepository extends JpaRepository<IncidentType, Long> {

    List<IncidentType> findAllByActiveIsTrueOrderByCreatedAt();

    List<IncidentType> findAllByOrderByCreatedAt();
}
