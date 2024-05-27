package com.example.portalbackend.api.dto.response.vote;

import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.ParticipantVote;

public record ParticipantVoteResponse(
        Long id,
        Boolean vote,
        UserResponse voteBy
) {
    public ParticipantVoteResponse(ParticipantVote participantVote) {
        this(
                participantVote.getId(),
                participantVote.getVote(),
                participantVote.getVoteBy() == null ? null : new UserResponse(participantVote.getVoteBy())
        );
    }
}
