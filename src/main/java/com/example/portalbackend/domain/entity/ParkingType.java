package com.example.portalbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingType extends AbstractEntity{

    private String type;
    @Column(columnDefinition = "text")
    private String description;
    private BigDecimal price;
    private String severity;

}
