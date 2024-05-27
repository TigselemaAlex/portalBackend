package com.example.portalbackend.api.dto.response.vote;

import com.example.portalbackend.api.dto.response.convocation.ConvocationResponse;
import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.AssemblyQuestion;

import java.util.List;

public record AssemblyQuestionResponse(

        Long id,
        String question,
        Integer totalVotes,
        Integer upVotes,
        Integer downVotes,
        Boolean enabled,
        UserResponse createdBy,
        ConvocationResponse convocation,
        List<ParticipantVoteResponse> votes
) {
    public AssemblyQuestionResponse(AssemblyQuestion assemblyQuestion) {
        this(
                assemblyQuestion.getId(),
                assemblyQuestion.getQuestion(),
                assemblyQuestion.getTotalVotes(),
                assemblyQuestion.getUpVotes(),
                assemblyQuestion.getDownVotes(),
                assemblyQuestion.getEnabled(),
                assemblyQuestion.getCreatedBy() == null ? null :
                new UserResponse(assemblyQuestion.getCreatedBy()),
                assemblyQuestion.getConvocation() == null ? null :
                new ConvocationResponse(assemblyQuestion.getConvocation(), 0, 0),
                assemblyQuestion.getVotes() == null ? null :
                assemblyQuestion.getVotes().stream().map(ParticipantVoteResponse::new).toList()
        );
    }
}
