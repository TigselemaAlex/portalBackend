package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.provider.ProviderCreateData;
import com.example.portalbackend.api.dto.request.provider.ProviderUpdateData;
import com.example.portalbackend.domain.entity.Provider;
import com.example.portalbackend.domain.repository.ProviderRepository;
import com.example.portalbackend.service.spec.IProviderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProviderService implements IProviderService {

    private final ProviderRepository providerRepository;

    @Override
    public List<Provider> findAll() {
        return providerRepository.findAllByOrderById();
    }

    @Override
    public List<Provider> findAllActive() {
        return providerRepository.findAllByActiveIsTrueOrderById();
    }

    @Override
    public Provider findById(Long id) {
        return providerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Provider save(ProviderCreateData data) {
        return providerRepository.save(
                Provider.builder()
                        .ruc(data.ruc())
                        .name(data.name())
                        .description(data.description())
                        .address(data.address())
                        .phone(data.phone())
                        .email(data.email())
                        .website(data.website())
                        .build()
        );
    }

    @Override
    public Provider update(Long id, ProviderUpdateData data) {

        Provider provider = findById(id);
        provider.setRuc(data.ruc());
        provider.setName(data.name());
        provider.setDescription(data.description());
        provider.setAddress(data.address());
        provider.setPhone(data.phone());
        provider.setEmail(data.email());
        provider.setWebsite(data.website());
        return providerRepository.save(provider);

    }

    @Override
    public void delete(Long id) {

        Provider provider = findById(id);
        providerRepository.delete(provider);
    }

    @Override
    public void reactivate(Long id) {

        Provider provider = findById(id);
        provider.setActive(true);
        providerRepository.save(provider);
    }
}
