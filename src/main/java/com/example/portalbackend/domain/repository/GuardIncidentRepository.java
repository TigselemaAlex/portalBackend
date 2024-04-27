package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.GuardIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuardIncidentRepository extends JpaRepository<GuardIncident, Long>, JpaSpecificationExecutor<GuardIncident> {
}
