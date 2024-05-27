package com.example.portalbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutcomeType extends AbstractEntity{
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private  String description;
    private Boolean canBeDeleted;
}
