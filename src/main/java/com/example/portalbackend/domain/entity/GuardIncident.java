package com.example.portalbackend.domain.entity;

import com.example.portalbackend.util.enumerate.GuardIncidentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuardIncident extends AbstractEntity{
    private String subject;
    @Column(columnDefinition = "text")
    private String description;
    private Calendar date;
    @Enumerated(EnumType.STRING)
    private GuardIncidentStatus status;
    @Column(columnDefinition = "text")
    private String observation;
    @ManyToOne
    private Guard guard;
    @ManyToOne
    private IncidentType type;
    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncidentEvidence> evidences = new ArrayList<>();

    public void addEvidence(IncidentEvidence evidence){
        if (this.evidences == null){
            this.evidences = new ArrayList<>();
        }
        this.evidences.add(evidence);
        evidence.setIncident(this);
    }
}
