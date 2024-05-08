package com.example.portalbackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssemblyQuestion extends AbstractEntity{

    private String question;
    private Integer totalVotes;
    private Integer upVotes;
    private Integer downVotes;
    private Boolean enabled;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Convocation convocation;
    @OneToMany(mappedBy = "assemblyQuestion")
    private List<ParticipantVote> votes;
}
