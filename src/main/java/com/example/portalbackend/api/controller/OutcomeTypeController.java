package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.outcome_type.OutcomeTypeData;
import com.example.portalbackend.api.usecase.OutcomeTypeUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected/outcome-type")
@RequiredArgsConstructor
public class OutcomeTypeController {

    private final OutcomeTypeUseCase outcomeTypeUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(){
        return outcomeTypeUseCase.findAll();
    }

    @GetMapping("/active")
    public ResponseEntity<CustomResponse<?>> findAllActive(){
        return outcomeTypeUseCase.findAllActive();
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> create(@Valid @RequestBody OutcomeTypeData data){
        return outcomeTypeUseCase.create(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(@PathVariable Long id, @Valid @RequestBody OutcomeTypeData data){
        return outcomeTypeUseCase.update(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id){
        return outcomeTypeUseCase.delete(id);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<CustomResponse<?>> activate(@PathVariable Long id){
        return outcomeTypeUseCase.activate(id);
    }
}
