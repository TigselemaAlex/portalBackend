package com.example.portalbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.util.Calendar;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE outcome SET active = false WHERE id = ?")
public class Outcome extends AbstractEntity{
    @Column(columnDefinition = "TEXT")
    private String description;
    private String code;
    private BigDecimal amount;
    private Calendar paidDate;
    @OneToOne(mappedBy = "outcome", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private PaidEvidence paidEvidence;
    @ManyToOne
    private Provider provider;
    @ManyToOne
    private OutcomeType type;
}
