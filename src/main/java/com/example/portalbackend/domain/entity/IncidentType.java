package com.example.portalbackend.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidentType extends AbstractEntity{
    private String name;
    private String severity;
    private String description;
}
