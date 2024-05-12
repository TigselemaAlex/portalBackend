package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.vote.AssemblyQuestionData;
import com.example.portalbackend.api.dto.request.vote.AssemblyQuestionUpdateVoteData;
import com.example.portalbackend.api.dto.request.vote.ParticipantVoteData;
import com.example.portalbackend.domain.entity.AssemblyQuestion;
import com.example.portalbackend.domain.entity.ParticipantVote;

import java.util.List;

public interface IAssemblyQuestionService {

    AssemblyQuestion createAssemblyQuestion(AssemblyQuestionData data);

    List<AssemblyQuestion> findAllAssemblyQuestions(Long id);
    List<AssemblyQuestion> findAllByConvocationAndEnabledIsTrueOrderById(Long id);

    AssemblyQuestion findAssemblyQuestionById(Long id);

    AssemblyQuestion updateAssemblyQuestion(Long id, AssemblyQuestionData data);

    void deleteAssemblyQuestion(Long id);

    AssemblyQuestion updateManuallyVoted(Long id, AssemblyQuestionUpdateVoteData data);

    AssemblyQuestion toggleEnabledVote(Long id);

    AssemblyQuestion updateVote(ParticipantVoteData vote);

    ParticipantVote getParticipantVote(Long id, Long userId);
}
