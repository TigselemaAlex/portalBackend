package com.example.portalbackend.api.usecase;

import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IFinancialObligationsServices;
import com.example.portalbackend.service.spec.IResidenceService;
import com.example.portalbackend.service.spec.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FinancialObligationsUseCase  extends AbstractUseCase{

    private final IFinancialObligationsServices financialObligationsServices;
    private final IUserService userService;
    private final IResidenceService residenceService;

    protected FinancialObligationsUseCase(CustomResponseBuilder customResponseBuilder, IFinancialObligationsServices financialObligationsServices, IUserService userService, IResidenceService residenceService) {
        super(customResponseBuilder);
        this.financialObligationsServices = financialObligationsServices;
        this.userService = userService;
        this.residenceService = residenceService;
    }

    public ResponseEntity<CustomResponse<?>> getFinancialObligations(Long user){
        return customResponseBuilder.build(HttpStatus.OK, "Obligaciones financieras", financialObligationsServices.getFinancialObligations(user));
    }

    public ResponseEntity<CustomResponse<?>> getFinancialObligationsStatus(Long id){

        return customResponseBuilder.build(HttpStatus.OK, "Obligaciones financieras", financialObligationsServices.getFinancialObligationsStatusByUser(userService.findById(id)));
    }

    public ResponseEntity<CustomResponse<?>> getFinancialObligationsStatusByResidence(Long residence){
        return customResponseBuilder.build(HttpStatus.OK, "Obligaciones financieras", financialObligationsServices.getFinancialObligationsStatusByResidence(residenceService.findById(residence)));
    }

    public ResponseEntity<CustomResponse<?>> getBlueParkingReport(){
        return  customResponseBuilder.build(HttpStatus.OK, "Cartera de parqueaderos azules", financialObligationsServices.getBlueParkingReport());
    }

    public ResponseEntity<CustomResponse<?>> getAliquotsReport(){
        return  customResponseBuilder.build(HttpStatus.OK, "Cartera de alicuotas", financialObligationsServices.getAliquotsReport());
    }
}
