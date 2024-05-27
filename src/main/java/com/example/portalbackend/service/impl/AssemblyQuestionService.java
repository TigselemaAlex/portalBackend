package com.example.portalbackend.service.impl;


import com.example.portalbackend.api.dto.request.vote.AssemblyQuestionData;
import com.example.portalbackend.api.dto.request.vote.AssemblyQuestionUpdateVoteData;
import com.example.portalbackend.api.dto.request.vote.ParticipantVoteData;
import com.example.portalbackend.domain.entity.*;
import com.example.portalbackend.domain.repository.AssemblyQuestionRepository;
import com.example.portalbackend.domain.repository.ParticipantVoteRepository;
import com.example.portalbackend.service.spec.IAssemblyQuestionService;
import com.example.portalbackend.service.spec.IConvocationService;
import com.example.portalbackend.service.spec.IRoleService;
import com.example.portalbackend.service.spec.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AssemblyQuestionService implements IAssemblyQuestionService {

    private final AssemblyQuestionRepository assemblyQuestionRepository;
    private final IUserService userService;
    private final IRoleService roleService;
    private final IConvocationService convocationService;
    private final ParticipantVoteRepository participantVoteRepository;

    @Override
    public AssemblyQuestion createAssemblyQuestion(AssemblyQuestionData data) {
        User user = userService.findById(data.createdBy());
        Convocation convocation = convocationService.findById(data.convocation());
        if (convocation.getFinalized()){
            return null;
        }
        return assemblyQuestionRepository.save(AssemblyQuestion.builder()
                        .createdBy(user)
                        .enabled(false)
                        .totalVotes(0)
                        .upVotes(0)
                        .downVotes(0)
                        .convocation(convocation)
                        .question(data.question())
                .build());
    }

    @Override

    @Transactional(readOnly = true)
    public List<AssemblyQuestion> findAllAssemblyQuestions(Long id) {
        Convocation convocation = convocationService.findById(id);
        return assemblyQuestionRepository.findAllByConvocationOrderById(convocation);
    }

    @Override
    public List<AssemblyQuestion> findAllByConvocationAndEnabledIsTrueOrderById(Long id) {
        Convocation convocation = convocationService.findById(id);
        return assemblyQuestionRepository.findAllByConvocationAndEnabledIsTrueOrderById(convocation);
    }

    @Override
    @Transactional(readOnly = true)
    public AssemblyQuestion findAssemblyQuestionById(Long id) {
        return assemblyQuestionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AssemblyQuestion updateAssemblyQuestion(Long id, AssemblyQuestionData data) {
        AssemblyQuestion assemblyQuestion = findAssemblyQuestionById(id);
        if (assemblyQuestion.getConvocation().getFinalized()){
            return null;
        }
        assemblyQuestion.setQuestion(data.question());
        return assemblyQuestionRepository.save(assemblyQuestion);
    }

    @Override
    public void deleteAssemblyQuestion(Long id) {
        AssemblyQuestion assemblyQuestion = findAssemblyQuestionById(id);
        assemblyQuestionRepository.delete(assemblyQuestion);
    }

    @Override
    public AssemblyQuestion updateManuallyVoted(Long id, AssemblyQuestionUpdateVoteData data) {
        return null;
    }

    @Override
    public AssemblyQuestion toggleEnabledVote(Long id) {
        AssemblyQuestion assemblyQuestion = findAssemblyQuestionById(id);
        if (assemblyQuestion.getConvocation().getFinalized()){
            return null;
        }
        assemblyQuestion.setEnabled(!assemblyQuestion.getEnabled());
        return assemblyQuestionRepository.save(assemblyQuestion);

    }

    @Override
    public AssemblyQuestion updateVote(ParticipantVoteData vote) {
        AssemblyQuestion assemblyQuestion = findAssemblyQuestionById(vote.assemblyQuestion());
        if(assemblyQuestion.getEnabled()){
            User user = userService.findById(vote.voteBy());
            Role tenant = roleService.findById(7L);
            for(AuthRole role : user.getAuthRoles()){
                if(role.getRole().getId().equals(tenant.getId())){
                    return null;
                }
            }
            ConvocationParticipant convocationParticipant = convocationService.findByConvocationIdAndResidenceUserId(assemblyQuestion.getConvocation().getId(), user.getId());
            if (!convocationParticipant.getAttendance()) {
                return null;
            }
            ParticipantVote participantByDevice = participantVoteRepository.findFirstByDeviceIdAndAssemblyQuestion(vote.deviceId(), assemblyQuestion).orElse(null);

            if (participantByDevice != null) {
                if (!Objects.equals(participantByDevice.getVoteBy().getId(), user.getId())) {
                    return null;
                }
            }
            ParticipantVote participant = participantVoteRepository.findByVoteByAndAssemblyQuestion(user, assemblyQuestion).orElse(null);
            if(participant == null){
                participant = ParticipantVote.builder()
                        .voteBy(user)
                        .assemblyQuestion(assemblyQuestion)
                        .vote(vote.vote())
                        .deviceId(vote.deviceId())
                        .build();
                assemblyQuestion.getVotes().add(participant);
                assemblyQuestion.setTotalVotes(assemblyQuestion.getTotalVotes() + 1);
                if(vote.vote()){
                    assemblyQuestion.setUpVotes(assemblyQuestion.getUpVotes() + 1);
                }else{
                    assemblyQuestion.setDownVotes(assemblyQuestion.getDownVotes() + 1);
                }
            }else{
                if(vote.vote() != participant.getVote()){
                    if(vote.vote()){
                        assemblyQuestion.setUpVotes(assemblyQuestion.getUpVotes() + 1);
                        assemblyQuestion.setDownVotes(assemblyQuestion.getDownVotes() - 1);
                    }else{
                        assemblyQuestion.setUpVotes(assemblyQuestion.getUpVotes() - 1);
                        assemblyQuestion.setDownVotes(assemblyQuestion.getDownVotes() + 1);
                    }
                    participant.setVote(vote.vote());
                    participant.setDeviceId(vote.deviceId());
                }
            }
        }else{
            return null;
        }
        return assemblyQuestionRepository.save(assemblyQuestion);
    }

    @Override
    public ParticipantVote getParticipantVote(Long id, Long userId) {
        AssemblyQuestion assemblyQuestion = findAssemblyQuestionById(id);
        User user = userService.findById(userId);
        return participantVoteRepository.findByVoteByAndAssemblyQuestion(user, assemblyQuestion).orElse(null);
    }
}
