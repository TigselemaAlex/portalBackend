package com.example.portalbackend.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRole  extends AbstractEntity{

    @ManyToOne
    private User user;
    @ManyToOne
    private Role role;
}
