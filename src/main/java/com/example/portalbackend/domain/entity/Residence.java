package com.example.portalbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Residence extends AbstractEntity{

    @Column(nullable = false, unique = true)
    private String number;

    @ManyToOne
    private User user;


    @ManyToOne
    @JoinColumn(nullable = false)
    private Passage passage;
}
