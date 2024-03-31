package com.example.portalbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Calendar;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialEvent extends AbstractEntity{
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    private String place;
    private Calendar date;
    private String imageUrl;
    private String fileName;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private User updatedBy;
}
