package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.income.IncomeCasualCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeCasualUpdateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesUpdateData;
import com.example.portalbackend.api.usecase.IncomeUseCase;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/protected/income")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
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
                    sort = "createdAt",
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
                    sort = "createdAt",
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
            @RequestParam(required = false) Long parking,
            @RequestParam Long incomeType,
            @RequestParam MultipartFile paidEvidence
                                                            ) throws IOException, FileUploadException, FirebaseMessagingException {
        return incomeUseCase.saveIncomeFees(new IncomeFeesCreateData(description, amount, paidDate, paidSince, paidUntil, residence, parking,incomeType, paidEvidence));
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
            @RequestParam(required = false) Long parking,
            @RequestParam Long incomeType,
            @RequestParam(required = false) MultipartFile paidEvidence
                                                              ) throws IOException, FileUploadException, FirebaseMessagingException {
        return incomeUseCase.updateIncomeFees(id, new IncomeFeesUpdateData(description, amount, paidDate, paidSince, paidUntil, residence,parking, incomeType, paidEvidence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> deleteIncome(@PathVariable Long id) {
        incomeUseCase.deleteIncome(id);
        return incomeUseCase.deleteIncome(id);
    }

    @GetMapping("/active/last")
    public ResponseEntity<CustomResponse<?>> getLastByResidenceAndType(
            @RequestParam Long residence,
            @RequestParam Long type,
            @RequestParam(required = false) Long parking)
    {
        return incomeUseCase.getLastByResidenceAndType(residence, type, parking);
    }

    @PostMapping("/casual")
    public ResponseEntity<CustomResponse<?>> saveIncomeCasual(
            @RequestParam String description,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam Long paidDate,
            @RequestParam Long residence,
            @RequestParam Long incomeType,
            @RequestParam(required = false) MultipartFile paidEvidence
                                                              ) throws IOException, FileUploadException, FirebaseMessagingException {
        return incomeUseCase.saveIncomeCasual(new IncomeCasualCreateData(description, amount, paidDate, residence, incomeType, paidEvidence));
    }

    @PutMapping("/casual/{id}")
    public ResponseEntity<CustomResponse<?>> updateIncomeCasual(
            @PathVariable Long id,
            @RequestParam String description,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam Long paidDate,
            @RequestParam Long residence,
            @RequestParam Long incomeType,
            @RequestParam(required = false) MultipartFile paidEvidence
                                                              ) throws IOException, FileUploadException, FirebaseMessagingException {
        return incomeUseCase.updateIncomeCasual(id, new IncomeCasualUpdateData(description, amount, paidDate, residence, incomeType, paidEvidence));
    }

}
