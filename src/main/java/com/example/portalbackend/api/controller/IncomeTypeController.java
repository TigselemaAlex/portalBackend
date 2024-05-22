package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.income_type.IncomeTypeCreateData;
import com.example.portalbackend.api.dto.request.income_type.IncomeTypeUpdateData;
import com.example.portalbackend.api.usecase.IncomeTypeUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected/income-type")
@RequiredArgsConstructor
public class IncomeTypeController {

    private final IncomeTypeUseCase incomeTypeUseCase;


    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(){
        return incomeTypeUseCase.findAll();
    }

    @GetMapping("/active")
    public ResponseEntity<CustomResponse<?>> findAllActive(){
        return incomeTypeUseCase.findAllActive();
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> create(@Valid @RequestBody IncomeTypeCreateData data){
        return incomeTypeUseCase.create(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(@Valid @RequestBody IncomeTypeUpdateData data, @PathVariable Long id){
        return incomeTypeUseCase.update(data, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id){
        return incomeTypeUseCase.delete(id);
    }

    @PutMapping("/reactivate/{id}")
    public ResponseEntity<CustomResponse<?>> reactivate(@PathVariable Long id){
        return incomeTypeUseCase.reactivate(id);
    }

    @GetMapping("/active/period")
    public ResponseEntity<CustomResponse<?>> findAllActiveByPeriod(@RequestParam IncomeTypePeriod period){
        return incomeTypeUseCase.findAllActiveByPeriod(period);
    }
}
