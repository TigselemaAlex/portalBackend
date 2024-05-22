package com.example.portalbackend.domain.entity;

import com.example.portalbackend.util.enumerate.PaidStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Penalty extends AbstractEntity{

    private String description;
    private BigDecimal amount;
    private Calendar issueDate;
    @ManyToOne
    private Residence residence;
    @Enumerated(EnumType.STRING)
    private PaidStatus status;
    @ManyToOne
    private PenaltyType type;
    @OneToMany(mappedBy = "penalty")
    private List<PenaltyEvidence> evidences;

    @OneToOne(mappedBy = "penalty")
    private PaidEvidence paidEvidence;
}


