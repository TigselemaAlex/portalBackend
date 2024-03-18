package com.example.portalbackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingGroup extends AbstractEntity{
    private String code;
    private String x;
    private String y;
    @ManyToOne
    private ParkingType type;
    @OneToMany(mappedBy = "group")
    private List<Parking> parkings;
}
