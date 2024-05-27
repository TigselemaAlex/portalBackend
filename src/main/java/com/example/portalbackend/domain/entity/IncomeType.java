package com.example.portalbackend.domain.entity;

import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE income_type SET active = false, updated_at = now() WHERE id = ?")
public class IncomeType extends AbstractEntity{
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private IncomeTypePeriod period;

    private Boolean canBeDeleted;
}
