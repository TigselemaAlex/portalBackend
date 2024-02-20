package com.example.portalbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.SQLDelete;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE role SET active = false WHERE id = ?")
public class Role extends AbstractEntity{
    @Column(unique = true, nullable = false, length = 80)
    private String name;
    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<AuthRole> authRoles;
}
