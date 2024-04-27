package com.example.portalbackend.service.spec;
import com.example.portalbackend.api.dto.request.incident_type.IncidentTypeCreateData;
import com.example.portalbackend.api.dto.request.incident_type.IncidentTypeUpdateData;
import com.example.portalbackend.domain.entity.IncidentType;
import java.util.List;
public interface IIncidentTypeService {
    List<IncidentType> getAllIncidentTypes();
    List<IncidentType> getActiveIncidentTypes();
    IncidentType getIncidentTypeById(Long id);
    IncidentType addIncidentType(IncidentTypeCreateData data);
    IncidentType updateIncidentType(Long id, IncidentTypeUpdateData data);
    void deleteIncidentType(Long id);
    void reactivateIncidentType(Long id);
}
