package com.example.portalbackend.domain.entity;

import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuardActivity extends AbstractEntity{
    private String subject;
    @Column(columnDefinition = "text")
    private String description;
    private Calendar startDate;
    private Calendar endDate;
    @Enumerated(EnumType.STRING)
    private GuardActivityStatus status;
    @Column(columnDefinition = "text")
    private String observation;
    @ManyToOne
    private Guard guard;
    @ManyToOne
    private User createdBy;
}
