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
@SQLDelete(sql = "UPDATE income SET active = false, updated_at = now() WHERE id = ?")
public class Income extends AbstractEntity{
    @Column(columnDefinition = "TEXT")
    private String code;
    private String description;
    private BigDecimal amount;
    private Calendar paidDate;
    private Integer monthsPaid;
    private Calendar paidSince;
    private Calendar paidUntil;
    private Boolean canBeDeleted;
    @ManyToOne
    private IncomeType type;
    @ManyToOne
    private Residence residence;
    @ManyToOne
    private Parking parking;
    @OneToOne(mappedBy = "income", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private PaidEvidence paidEvidence;

}
