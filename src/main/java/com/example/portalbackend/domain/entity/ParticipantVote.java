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
public class ParticipantVote extends AbstractEntity {
    private Boolean vote;

    private String deviceId;

    @ManyToOne
    private AssemblyQuestion assemblyQuestion;
    @ManyToOne
    private User voteBy;


}
