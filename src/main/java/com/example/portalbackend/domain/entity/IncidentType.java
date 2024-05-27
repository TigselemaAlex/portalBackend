package com.example.portalbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE incident_type SET active = false, updated_at = now() WHERE  id = ? ")
public class IncidentType extends AbstractEntity{
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String severity;
    @Column(columnDefinition = "TEXT")
    private String description;

}
