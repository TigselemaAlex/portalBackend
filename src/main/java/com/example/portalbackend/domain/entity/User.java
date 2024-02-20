package com.example.portalbackend.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE auth SET active = false, updated_at = now() WHERE  id = ? ")
public class User extends AbstractEntity{

    @Column(nullable = false)
    private String names;
    @Column(nullable = false)
    private String surnames;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 10)
    private String phone;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 15, unique = true)
    private String dni;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<AuthRole> authRoles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Residence> residences;

}
