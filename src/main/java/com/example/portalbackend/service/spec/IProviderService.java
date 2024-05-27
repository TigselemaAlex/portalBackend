package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.provider.ProviderCreateData;
import com.example.portalbackend.api.dto.request.provider.ProviderUpdateData;
import com.example.portalbackend.domain.entity.Provider;

import java.util.List;

public interface IProviderService {

    List<Provider> findAll();

    List<Provider> findAllActive();

    Provider findById(Long id);

    Provider save(ProviderCreateData data);

    Provider update(Long id, ProviderUpdateData data);

    void delete(Long id);

    void reactivate(Long id);
}
