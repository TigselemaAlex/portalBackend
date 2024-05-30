package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.outcome_type.OutcomeTypeData;
import com.example.portalbackend.api.dto.response.outcome_type.OutcomeTypeResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IOutcomeTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutcomeTypeUseCase extends AbstractUseCase{

    private final IOutcomeTypeService outcomeTypeService;

    protected OutcomeTypeUseCase(CustomResponseBuilder customResponseBuilder, IOutcomeTypeService outcomeTypeService) {
        super(customResponseBuilder);
        this.outcomeTypeService = outcomeTypeService;
    }

    public ResponseEntity<CustomResponse<?>> findAll(){
        List<OutcomeTypeResponse> responses = outcomeTypeService.findAll().stream().map(OutcomeTypeResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Tipos de egresos encontrados", responses);
    }

    public ResponseEntity<CustomResponse<?>> findAllActive(){
        List<OutcomeTypeResponse> responses = outcomeTypeService.findAllActive().stream().map(OutcomeTypeResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Tipos de egresos activos encontrados", responses);
    }

    public ResponseEntity<CustomResponse<?>> create(OutcomeTypeData data){
        outcomeTypeService.save(data);
        return customResponseBuilder.build(HttpStatus.CREATED, "Tipo de egreso creado");
    }

    public ResponseEntity<CustomResponse<?>> update(Long id, OutcomeTypeData data){
        outcomeTypeService.update(id, data);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de egreso actualizado");
    }

    public ResponseEntity<CustomResponse<?>> delete(Long id){
        outcomeTypeService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de egreso inhabilitado");
    }

    public ResponseEntity<CustomResponse<?>> activate(Long id){
        outcomeTypeService.activate(id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de egreso habilitado");
    }




}
