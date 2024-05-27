package com.example.portalbackend.domain.entity;

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
@SQLDelete(sql = "UPDATE provider SET active = false WHERE id = ?")
public class Provider extends AbstractEntity{
    @Column(unique = true)
    private String name;
    private String description;
    @Column(unique = true)
    private String ruc;
    private String address;
    private String phone;
    private String email;
    private String website;
    @OneToMany(mappedBy = "provider")
    private List<Outcome> outcomes;
}
