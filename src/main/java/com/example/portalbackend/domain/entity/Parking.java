package com.example.portalbackend.domain.entity;

import com.example.portalbackend.util.enumerate.ParkingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parking extends AbstractEntity{

    private String code;
    @Enumerated(EnumType.STRING)
    private ParkingStatus status;
    @ManyToOne
    private ParkingGroup group;
    @ManyToOne
    private Residence residence;

}
