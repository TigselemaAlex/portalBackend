package com.example.portalbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssemblyQuestion extends AbstractEntity{

    @Column(columnDefinition = "TEXT")
    private String question;
    private Integer totalVotes;
    private Integer upVotes;
    private Integer downVotes;
    private Boolean enabled;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Convocation convocation;
    @OneToMany(mappedBy = "assemblyQuestion", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ParticipantVote> votes;
}
