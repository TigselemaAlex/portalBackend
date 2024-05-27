package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.GuardIncident;
import com.example.portalbackend.domain.entity.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Calendar;

public interface GuardIncidentRepository extends JpaRepository<GuardIncident, Long>, JpaSpecificationExecutor<GuardIncident> {
    Long countByDateBetweenAndType(Calendar start, Calendar end, IncidentType type);
}
