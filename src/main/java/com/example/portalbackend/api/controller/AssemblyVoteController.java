package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.vote.AssemblyQuestionData;
import com.example.portalbackend.api.dto.request.vote.ParticipantVoteData;
import com.example.portalbackend.api.usecase.AssemblyQuestionUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/protected/assembly-votes")
@RequiredArgsConstructor
public class AssemblyVoteController {

    private final AssemblyQuestionUseCase assemblyQuestionUseCase;

    @PostMapping
    public ResponseEntity<CustomResponse<?>> createAssemblyQuestion(@Valid @RequestBody AssemblyQuestionData data) {
        return assemblyQuestionUseCase.createAssemblyQuestion(data);
    }

    @GetMapping("/convocation/{id}")
    public ResponseEntity<CustomResponse<?>> findAllAssemblyQuestions(@PathVariable Long id) {
        return assemblyQuestionUseCase.findAllAssemblyQuestions(id);
    }

    @GetMapping("/convocation-enabled/{id}")
    public ResponseEntity<CustomResponse<?>> findAllByConvocationAndEnabledIsTrueOrderById(@PathVariable Long id) {
        return assemblyQuestionUseCase.findAllByConvocationAndEnabledIsTrueOrderById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> updateAssemblyQuestion(@PathVariable Long id, @Valid @RequestBody AssemblyQuestionData data) {
        return assemblyQuestionUseCase.updateAssemblyQuestion(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> deleteAssemblyQuestion(@PathVariable Long id) {
        return assemblyQuestionUseCase.deleteAssemblyQuestion(id);
    }

    @PutMapping("/toggle-enabled-vote/{id}")
    public ResponseEntity<CustomResponse<?>> toggleEnabledVote(@PathVariable Long id) throws FirebaseMessagingException {
        return assemblyQuestionUseCase.toggleEnabledVote(id);
    }

    @PutMapping("/update-vote")
    public ResponseEntity<CustomResponse<?>> updateVote(@Valid @RequestBody ParticipantVoteData vote) throws IOException {
        return assemblyQuestionUseCase.updateVote(vote);
    }

    @GetMapping("/participant-vote/{id}/{userId}")
    public ResponseEntity<CustomResponse<?>> getParticipantVote(@PathVariable Long id, @PathVariable Long userId) {
        return assemblyQuestionUseCase.getParticipantVote(id, userId);
    }

}
