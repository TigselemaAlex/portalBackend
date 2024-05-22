package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.income.IncomeFeesCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesUpdateData;
import com.example.portalbackend.api.usecase.IncomeUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.util.enumerate.PaidStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/protected/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeUseCase incomeUseCase;

    @GetMapping("/monthly")
    public ResponseEntity<CustomResponse<?>> getIncomesByFiltersMonthly(
            @RequestParam(required = false) Long type,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to,
            @RequestParam(required = false) Long residence,
            @PageableDefault(
                    size = 20,
                    sort = "paidDate",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {
        return incomeUseCase.getIncomesByFiltersMonthly(type, from, to, residence, pageable);
    }

    @GetMapping("/casual")
    public ResponseEntity<CustomResponse<?>> getIncomesByFiltersCasual(
            @RequestParam(required = false) Long type,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to,
            @RequestParam(required = false) Long residence,
            @PageableDefault(
                    size = 20,
                    sort = "paidDate",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {
        return incomeUseCase.getIncomesByFiltersCasual(type, from, to, residence, pageable);
    }

    @PostMapping("/fees")
    public ResponseEntity<CustomResponse<?>> saveIncomeFees(
            @RequestParam String description,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam Long paidDate,
            @RequestParam Long paidSince,
            @RequestParam Long paidUntil,
            @RequestParam Long residence,
            @RequestParam Long incomeType,
            @RequestParam MultipartFile paidEvidence
                                                            ) throws IOException, FileUploadException {
        return incomeUseCase.saveIncomeFees(new IncomeFeesCreateData(description, amount, paidDate, paidSince, paidUntil, residence, incomeType, paidEvidence));
    }

    @PutMapping("/fees/{id}")
    public ResponseEntity<CustomResponse<?>> updateIncomeFees(
            @PathVariable Long id,
            @RequestParam String description,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam Long paidDate,
            @RequestParam Long paidSince,
            @RequestParam Long paidUntil,
            @RequestParam Long residence,
            @RequestParam Long incomeType,
            @RequestParam MultipartFile paidEvidence
                                                              ) throws IOException, FileUploadException {
        return incomeUseCase.updateIncomeFees(id, new IncomeFeesUpdateData(description, amount, paidDate, paidSince, paidUntil, residence, incomeType, paidEvidence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> deleteIncome(@PathVariable Long id) {
        incomeUseCase.deleteIncome(id);
        return incomeUseCase.deleteIncome(id);
    }

}
