package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.response.financial_obligation.FinancialObligationResponse;
import com.example.portalbackend.api.dto.response.financial_obligation.FinancialObligationStatusResponse;
import com.example.portalbackend.api.dto.response.income.IncomeResponse;
import com.example.portalbackend.api.dto.response.penalty.PenaltyResponse;
import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.domain.entity.*;
import com.example.portalbackend.domain.repository.IncomeRepository;
import com.example.portalbackend.domain.repository.ParkingRepository;
import com.example.portalbackend.domain.repository.PenaltyRepository;
import com.example.portalbackend.domain.repository.ResidenceRepository;
import com.example.portalbackend.service.spec.IFinancialObligationsServices;
import com.example.portalbackend.service.spec.IIncomeTypeService;
import com.example.portalbackend.service.spec.IUserService;
import com.example.portalbackend.util.calendar.CalendarUtil;

import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import com.example.portalbackend.util.enumerate.PaidStatus;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@EnableScheduling
public class FinancialObligationsService implements IFinancialObligationsServices {

    private final IUserService userService;
    private final IncomeRepository incomeRepository;
    private final PenaltyRepository penaltyRepository;
    private final IIncomeTypeService incomeTypeService;
    private final PushNotificationService pushNotificationService;
    private final ParkingRepository parkingRepository;
    private final ResidenceRepository residenceRepository;
    @Override
    public FinancialObligationResponse getFinancialObligations(Long id) {
        User user = userService.findById(id);
        List<FinancialObligationResponse.FinancialObligationPerResidence> residencesObligations = new ArrayList<>();
        List<FinancialObligationResponse.PenaltiesPerResidence> residencesPaidPenalties = new ArrayList<>();
        List<FinancialObligationResponse.PenaltiesPerResidence> residencesUnpaidPenalties =new ArrayList<>();
        List<FinancialObligationResponse.PenaltiesUnpaidAmountPerResidence> residencesUnpaidAmounts = new ArrayList<>();

        user.getResidences().forEach(
                residence -> {
                    List<IncomeResponse> residencesObligationsDB = incomeRepository.findTop10ByActiveIsTrueAndResidenceAndTypePeriodOrderByPaidDateDesc(residence, IncomeTypePeriod.MONTHLY)
                            .stream()
                            .map(IncomeResponse::new)
                            .toList();
                    List<PenaltyResponse> paidPenalties = penaltyRepository.findTop10ByActiveIsTrueAndStatusAndResidenceOrderByPaidDateDesc(PaidStatus.PAID, residence)
                            .stream()
                            .map(PenaltyResponse::new)
                            .toList();
                    List<PenaltyResponse> unpaidPenalties = penaltyRepository.findAllByActiveIsTrueAndStatusAndResidenceOrderByIssueDateDesc(PaidStatus.UNPAID, residence)
                            .stream()
                            .map(PenaltyResponse::new)
                            .toList();

                    Double unpaidAmountDB = penaltyRepository.sumTotalPenaltiesByResidence(residence);
                    BigDecimal unpaidAmount = BigDecimal.valueOf(unpaidAmountDB != null ? unpaidAmountDB : 0);

                    residencesObligations.add(FinancialObligationResponse.FinancialObligationPerResidence.builder()
                            .residence(new ResidenceResponse(residence))
                            .incomes(residencesObligationsDB)
                            .build());

                    residencesPaidPenalties.add(FinancialObligationResponse.PenaltiesPerResidence.builder()
                            .residence(new ResidenceResponse(residence))
                            .penalties(paidPenalties)
                            .build());

                    residencesUnpaidPenalties.add(FinancialObligationResponse.PenaltiesPerResidence.builder()
                            .residence(new ResidenceResponse(residence))
                            .penalties(unpaidPenalties)
                            .build());

                    residencesUnpaidAmounts.add(FinancialObligationResponse.PenaltiesUnpaidAmountPerResidence.builder()
                            .residence(new ResidenceResponse(residence))
                            .amount(unpaidAmount)
                            .build());

                }
        );
        return new FinancialObligationResponse(residencesObligations, residencesPaidPenalties, residencesUnpaidPenalties, residencesUnpaidAmounts);
    }



    @Scheduled(cron = "0 0 13 25 * ?", zone = "America/Guayaquil")
    public void getFinancialObligationsStatus() {
        List<User> users = userService.findAllActive("", "", "");
        users.forEach(
                user -> {
                    FinancialObligationStatusResponse financialObligationsStatusByUser = getFinancialObligationsStatusByUser(user);
                    try {
                        HashMap<String, String> data = new HashMap<>();
                        data.put("type", "financial_obligations");
                        pushNotificationService.sendPushNotification(user.getId(), "Obligaciones financieras", createMessage(financialObligationsStatusByUser), data);
                    } catch (FirebaseMessagingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public FinancialObligationStatusResponse getFinancialObligationsStatusByResidence(Residence residence) {
        IncomeType aliquotType = incomeTypeService.findById(1L);
        Income aliquot = incomeRepository.findFirstByActiveIsTrueAndResidenceAndTypeOrderByPaidUntilDesc(residence, aliquotType)
                .orElse(null);
        Integer aliquotDelayed = -1;
        Calendar now = Calendar.getInstance();
        List<FinancialObligationStatusResponse.FinancialStatusDetail> financialStatusDetails = new ArrayList<>();

        if (aliquot != null) {
            aliquotDelayed = 0;
            if(aliquot.getPaidUntil().before(now)){
                aliquotDelayed = CalendarUtil.getMonthDifference(aliquot.getPaidUntil(), now) - 1;
            }
        }
        List<FinancialObligationStatusResponse.FinancialStatusDetailComplement> complements = new ArrayList<>();
        complements.add(FinancialObligationStatusResponse.FinancialStatusDetailComplement.builder()
                .type("ALICUOTAS")
                .totalMonthsDelayed(aliquotDelayed)
                .build());
        financialStatusDetails.add(FinancialObligationStatusResponse.FinancialStatusDetail.builder()
                .complement(complements)
                .residence(new ResidenceResponse(residence))
                .build());
        return new FinancialObligationStatusResponse(financialStatusDetails);
    }

    @Override
    public List<IncomeResponse> getBlueParkingReport() {
        IncomeType blueParkingType = incomeTypeService.findById(2L);
        List<Parking> occupiedParkings = parkingRepository.findAllByResidenceIsNotNull();
        List<Income> incomes = new ArrayList<>();
        occupiedParkings.forEach(
                parking -> {
                    Optional<Income> incomeOptional = incomeRepository
                            .findFirstByActiveIsTrueAndParkingAndTypeOrderByPaidUntilDesc(parking, blueParkingType);
                    incomeOptional.ifPresent(incomes::add);
                }
        );
        return incomes.stream().sorted(
                Comparator.comparing(o -> o.getParking().getId())
        ).map(IncomeResponse::new).toList();
    }

    @Override
    public List<IncomeResponse> getAliquotsReport() {
        IncomeType aliquotType = incomeTypeService.findById(1L);
        List<Residence> residences = residenceRepository.findAll();
        List<Income> incomes = new ArrayList<>();
        residences.forEach(
                residence -> {
                    Optional<Income> incomeOptional = incomeRepository
                            .findFirstByActiveIsTrueAndResidenceAndTypeOrderByPaidUntilDesc(residence, aliquotType);
                    incomeOptional.ifPresent(incomes::add);
                }
        );
        return incomes.stream().sorted(
                Comparator.comparing(o -> o.getResidence().getId())
        ).map(IncomeResponse::new).toList();
    }

    @Override
    public FinancialObligationStatusResponse getFinancialObligationsStatusByUser(User user) {
        IncomeType aliquotType = incomeTypeService.findById(1L);
        IncomeType blueParkingType = incomeTypeService.findById(2L);
        Calendar now = Calendar.getInstance();
        List<FinancialObligationStatusResponse.FinancialStatusDetail> financialStatusDetails = new ArrayList<>();
        if (user.getResidences().isEmpty()) {
            return new FinancialObligationStatusResponse(financialStatusDetails);
        }
        user.getResidences().forEach(
                residence -> {
                    Income aliquot = incomeRepository.findFirstByActiveIsTrueAndResidenceAndTypeOrderByPaidUntilDesc(residence, aliquotType)
                            .orElse(null);

                     List<Income> blueParkingList = new ArrayList<>();

                     residence.getParkings().forEach(
                        parking -> {
                            Income blueParking = incomeRepository.findFirstByActiveIsTrueAndResidenceAndTypeAndParkingOrderByPaidUntilDesc(residence, blueParkingType, parking)
                                    .orElse(null);
                            if (blueParking != null){
                                blueParkingList.add(blueParking);
                            }
                        });
                    Integer aliquotDelayed = -1;

                    if (aliquot != null) {
                        aliquotDelayed = 0;
                        if(aliquot.getPaidUntil().before(now)){
                            aliquotDelayed = CalendarUtil.getMonthDifference(aliquot.getPaidUntil(), now) - 1;
                        }
                    }

                    List<FinancialObligationStatusResponse.FinancialStatusComplementParkingData> parkingData = new ArrayList<>();
                    List<FinancialObligationStatusResponse.FinancialStatusDetailComplement> complements = new ArrayList<>();
                    complements.add(FinancialObligationStatusResponse.FinancialStatusDetailComplement.builder()
                            .type("ALICUOTAS")
                            .totalMonthsDelayed(aliquotDelayed)
                            .build());

                    blueParkingList.forEach(
                            blueParking -> {
                                Integer blueParkingDelayed = 0;
                                if(blueParking.getPaidUntil().before(now)){
                                    blueParkingDelayed = CalendarUtil.getMonthDifference(blueParking.getPaidUntil(), now) - 1;
                                }
                                parkingData.add(FinancialObligationStatusResponse.FinancialStatusComplementParkingData.builder()
                                        .code(blueParking.getParking().getCode())
                                        .totalMonthsDelayed(blueParkingDelayed)
                                        .build());

                            }
                    );

                    complements.add(FinancialObligationStatusResponse.FinancialStatusDetailComplement.builder()
                            .type("PARQUEADERO AZUL")
                            .parkingData(parkingData)
                            .build());

                    financialStatusDetails.add(FinancialObligationStatusResponse.FinancialStatusDetail.builder()
                            .complement(complements)
                            .residence(new ResidenceResponse(residence))
                            .build());

                }
        );
        return new FinancialObligationStatusResponse(financialStatusDetails);
    }


    private String createMessage(FinancialObligationStatusResponse status){
        String message = "Obligaciones financieras pendientes:\n";
        for (FinancialObligationStatusResponse.FinancialStatusDetail detail : status.financialStatusDetails()) {

            message += "Casa N°: " + detail.getResidence().number() + "\n";
            for (FinancialObligationStatusResponse.FinancialStatusDetailComplement complement : detail.getComplement()) {
                message += "Tipo: " + complement.getType().toLowerCase() + " -> ";
                Integer totalMonthsDelayed = complement.getTotalMonthsDelayed();
                message += "Meses de atraso: " + (totalMonthsDelayed<0 ? "Sin registro" : totalMonthsDelayed) + "\n";
            }
        }
        return message;
    }


}
