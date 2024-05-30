package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.usecase.FinancialObligationsUseCase;
import com.example.portalbackend.common.CustomResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected/financial-obligations")
@RequiredArgsConstructor
public class FinancialObligationsController {

    private final FinancialObligationsUseCase financialObligationsUseCase;


    @GetMapping("/status/{id}")
    public ResponseEntity<CustomResponse<?>> getFinancialObligationsStatus(@PathVariable Long id) {
        return financialObligationsUseCase.getFinancialObligations(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> getFinancialObligations(@PathVariable Long id) {
        return financialObligationsUseCase.getFinancialObligationsStatus(id);
    }
}
