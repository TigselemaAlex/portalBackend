package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.AssemblyQuestion;
import com.example.portalbackend.domain.entity.ParticipantVote;
import com.example.portalbackend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantVoteRepository extends JpaRepository<ParticipantVote, Long>{

    Optional<ParticipantVote> findByVoteByAndAssemblyQuestion(User voteBy, AssemblyQuestion assemblyQuestion);
    Optional<ParticipantVote> findFirstByDeviceIdAndAssemblyQuestion(String deviceId, AssemblyQuestion assemblyQuestion);
}
