package com.example.portalbackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Calendar;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidenceHistory extends AbstractEntity{
    private Calendar startDate;
    private Calendar finishDate;
    @ManyToOne
    private Residence residence;
    @ManyToOne
    private User user;
}
