package com.example.portalbackend.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE guard SET active = false, updated_at = now() WHERE  id = ? ")
public class Guard extends AbstractEntity{
    private String fullName;
    @Column(unique = true)
    private String dni;
    private String photoUrl;
    private String photoName;
    private String phone;
    @OneToMany(mappedBy = "guard", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<GuardActivity> activities;
    @OneToMany(mappedBy = "guard", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<GuardIncident> incidents;
}
