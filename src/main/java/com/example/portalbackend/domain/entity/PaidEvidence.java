package com.example.portalbackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaidEvidence extends AbstractEntity{
    private String fileName;
    private String fileUrl;
    @OneToOne
    private Penalty penalty;
    @OneToOne
    private Income income;
}
