package com.example.portalbackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidentEvidence extends AbstractEntity{
    private String fileName;
    private String fileUrl;
    private @ManyToOne
    GuardIncident incident;
}
