package com.example.portalbackend.domain.entity;

import com.example.portalbackend.util.enumerate.PaidStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE penalty SET active = false, updated_at = now() WHERE id = ?")
public class Penalty extends AbstractEntity {

    private String description;
    private String code;
    private BigDecimal amount;
    private Calendar issueDate;
    private Calendar paidDate;
    @ManyToOne
    private Residence residence;
    @Enumerated(EnumType.STRING)
    private PaidStatus status;
    @ManyToOne
    private PenaltyType type;
    @OneToMany(mappedBy = "penalty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PenaltyEvidence> evidences;
    @OneToOne(mappedBy = "penalty", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private PaidEvidence paidEvidence;

    public void addEvidence(PenaltyEvidence evidence) {
        if (this.evidences == null) {
            this.evidences = new ArrayList<>();
        }
        this.evidences.add(evidence);
        evidence.setPenalty(this);
    }
}


