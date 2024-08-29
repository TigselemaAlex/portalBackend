package com.example.portalbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE outcome_type SET active = false WHERE id = ?")
public class OutcomeType extends AbstractEntity{
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private  String description;
}
