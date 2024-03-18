package com.example.portalbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "residence")
    private List<Parking> parkings;
}
