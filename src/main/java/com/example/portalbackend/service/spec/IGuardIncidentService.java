package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.guard_incident.GuardIncidentCreateData;
import com.example.portalbackend.api.dto.request.guard_incident.GuardIncidentUpdateData;
import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.entity.GuardIncident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Calendar;

public interface IGuardIncidentService {
    GuardIncident create(GuardIncidentCreateData data);
    GuardIncident update(Long id, GuardIncidentUpdateData data);
    void delete(Long id);
    GuardIncident findById(Long id);
    Page<GuardIncident> findAll(String subject, Guard guard, Calendar date, Pageable pageable);
}
