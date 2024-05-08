package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.ParticipantVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantVoteRepository extends JpaRepository<ParticipantVote, Long>{
}
