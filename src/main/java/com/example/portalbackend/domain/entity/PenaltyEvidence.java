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
public class PenaltyEvidence extends AbstractEntity
{
    private String fileName;
    private String fileUrl;
    @ManyToOne
    private Penalty penalty;
}
