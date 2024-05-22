package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.income_type.IncomeTypeCreateData;
import com.example.portalbackend.api.dto.request.income_type.IncomeTypeUpdateData;
import com.example.portalbackend.api.dto.response.income_type.IncomeTypeResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IIncomeTypeService;
import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncomeTypeUseCase extends AbstractUseCase{

    private final IIncomeTypeService incomeTypeService;

    protected IncomeTypeUseCase(CustomResponseBuilder customResponseBuilder, IIncomeTypeService incomeTypeService) {
        super(customResponseBuilder);
        this.incomeTypeService = incomeTypeService;
    }

    public ResponseEntity<CustomResponse<?>> findAll(){
        List<IncomeTypeResponse> responses = incomeTypeService.findAll().stream().map(IncomeTypeResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de tipos de ingresos", responses);
    }

    public ResponseEntity<CustomResponse<?>> findAllActive(){
        List<IncomeTypeResponse> responses = incomeTypeService.findAllActive().stream().map(IncomeTypeResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de tipos de ingresos activos", responses);
    }

    public ResponseEntity<CustomResponse<?>> create(IncomeTypeCreateData data){
        incomeTypeService.save(data);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de ingreso creado", null);
    }

    public ResponseEntity<CustomResponse<?>> update(IncomeTypeUpdateData data, Long id){
        incomeTypeService.update(data, id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de ingreso actualizado", null);
    }

    public ResponseEntity<CustomResponse<?>> delete(Long id){
        incomeTypeService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de ingreso inhabilitado", null);
    }

    public ResponseEntity<CustomResponse<?>> reactivate(Long id){
        incomeTypeService.reactivate(id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de ingreso habilitado", null);
    }

    public ResponseEntity<CustomResponse<?>> findAllActiveByPeriod(IncomeTypePeriod period){
        List<IncomeTypeResponse> responses = incomeTypeService.findAllActiveByPeriod(period).stream().map(IncomeTypeResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de tipos de ingresos activos por periodo", responses);
    }
}
