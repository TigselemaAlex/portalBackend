package com.example.portalbackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Minutes extends AbstractEntity{
    private String fileUrl;
    private String fileName;
    @ManyToOne
    private Convocation convocation;
}
