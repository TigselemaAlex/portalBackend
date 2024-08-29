package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.provider.ProviderCreateData;
import com.example.portalbackend.api.dto.request.provider.ProviderUpdateData;
import com.example.portalbackend.api.dto.response.provider.ProviderResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProviderUseCase extends AbstractUseCase{

    private final IProviderService providerService;

    protected ProviderUseCase(CustomResponseBuilder customResponseBuilder, IProviderService providerService) {
        super(customResponseBuilder);
        this.providerService = providerService;
    }

    public ResponseEntity<CustomResponse<?>> findAll() {
        List<ProviderResponse> responses = providerService.findAll().stream().map(ProviderResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de todos los proveedores", responses);
    }

    public ResponseEntity<CustomResponse<?>> findAllActive() {
        List<ProviderResponse> responses = providerService.findAllActive().stream().map(ProviderResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de todos los proveedores activos", responses);
    }

    public ResponseEntity<CustomResponse<?>> findById(Long id) {
        ProviderResponse response = new ProviderResponse(providerService.findById(id));
        return customResponseBuilder.build(HttpStatus.OK, "Proveedor encontrado", response);
    }

    public ResponseEntity<CustomResponse<?>> save(ProviderCreateData data) {
        ProviderResponse response = new ProviderResponse(providerService.save(data));
        return customResponseBuilder.build(HttpStatus.CREATED, "Proveedor creado");
    }

    public ResponseEntity<CustomResponse<?>> update(Long id, ProviderUpdateData data) {
        ProviderResponse response = new ProviderResponse(providerService.update(id, data));
        return customResponseBuilder.build(HttpStatus.OK, "Proveedor actualizado");
    }

    public ResponseEntity<CustomResponse<?>> delete(Long id) {
        providerService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Proveedor eliminado");
    }

    public ResponseEntity<CustomResponse<?>> reactivate(Long id){
        providerService.reactivate(id);
        return customResponseBuilder.build(HttpStatus.OK, "Proveedor reactivado");
    }
}
