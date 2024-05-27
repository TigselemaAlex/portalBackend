package com.example.portalbackend.api.usecase;

import com.amazonaws.Response;
import com.example.portalbackend.api.dto.request.penalty_type.PenaltyTypeCreateData;
import com.example.portalbackend.api.dto.request.penalty_type.PenaltyTypeUpdateData;
import com.example.portalbackend.api.dto.response.penalty_type.PenaltyTypeResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IPenaltyTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PenaltyTypeUseCase extends AbstractUseCase{

    private final IPenaltyTypeService penaltyTypeService;

    protected PenaltyTypeUseCase(CustomResponseBuilder customResponseBuilder, IPenaltyTypeService penaltyTypeService) {
        super(customResponseBuilder);
        this.penaltyTypeService = penaltyTypeService;
    }

    public ResponseEntity<CustomResponse<?>> findAll(){

        List<PenaltyTypeResponse> responses = penaltyTypeService.findAll().stream()
                .map(PenaltyTypeResponse::new)
                .toList();
        return customResponseBuilder.build(HttpStatus.OK ," Listado de tipos de penalidades", responses);
    }

    public ResponseEntity<CustomResponse<?>> findAllActive(){
        List<PenaltyTypeResponse> responses = penaltyTypeService.findAllActive().stream()
                .map(PenaltyTypeResponse::new)
                .toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de tipos de penalidades activos", responses);
    }

    public ResponseEntity<CustomResponse<?>> create(PenaltyTypeCreateData data){
        penaltyTypeService.save(data);
        return customResponseBuilder.build(HttpStatus.CREATED, "Tipo de penalidad creado");
    }

    public ResponseEntity<CustomResponse<?>> update(Long id, PenaltyTypeUpdateData data){
        penaltyTypeService.update(id, data);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de penalidad actualizado");
    }

    public ResponseEntity<CustomResponse<?>> delete(Long id){
        penaltyTypeService.deleteById(id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de penalidad eliminado");
    }

    public ResponseEntity<CustomResponse<?>> reactivate(Long id){
        penaltyTypeService.reactivateById(id);
        return customResponseBuilder.build(HttpStatus.OK, "Tipo de penalidad reactivado");
    }
}
