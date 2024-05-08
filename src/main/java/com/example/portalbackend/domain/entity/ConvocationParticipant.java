package com.example.portalbackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Calendar;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvocationParticipant extends AbstractEntity {

    private Boolean attendance;
    private Calendar attendanceDate;
    private String participant;
    @ManyToOne
    private Residence residence;
    @ManyToOne
    private Convocation convocation;
}
