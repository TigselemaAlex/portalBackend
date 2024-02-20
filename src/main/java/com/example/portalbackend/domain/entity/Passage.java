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
@SQLDelete(sql = "UPDATE passage SET active = false, updated_at = now() WHERE  id = ? ")
public class Passage extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
