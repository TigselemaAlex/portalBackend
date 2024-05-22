package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.income_type.IncomeTypeCreateData;
import com.example.portalbackend.api.dto.request.income_type.IncomeTypeUpdateData;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.domain.repository.IncomeTypeRepository;
import com.example.portalbackend.service.spec.IIncomeTypeService;
import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeTypeService implements IIncomeTypeService {

    private final IncomeTypeRepository incomeTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<IncomeType> findAll() {
        return incomeTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncomeType> findAllActive() {
        return incomeTypeRepository.findAllByActiveIsTrue();
    }

    @Override
    public List<IncomeType> findAllActiveByPeriod(IncomeTypePeriod period) {
        return incomeTypeRepository.findAllByActiveIsTrueAndPeriod(period);
    }

    @Override
    @Transactional(readOnly = true)
    public IncomeType findById(Long id) {
        return incomeTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public IncomeType save(IncomeTypeCreateData data) {
        IncomeType incomeType = IncomeType.builder()
                .name(data.name())
                .price(data.price())
                .period(data.period())
                .canBeDeleted(true)
                .description(data.description())
                .build();
        return incomeTypeRepository.save(incomeType);
    }

    @Override
    public IncomeType update(IncomeTypeUpdateData data, Long id) {
        IncomeType incomeType = findById(id);
        incomeType.setName(data.name());
        incomeType.setPrice(data.price());
        incomeType.setPeriod(data.period());
        incomeType.setDescription(data.description());
        return incomeTypeRepository.save(incomeType);
    }

    @Override
    public void delete(Long id) {
        IncomeType incomeType = findById(id);
        if (incomeType.getCanBeDeleted()){
            incomeTypeRepository.delete(incomeType);
        }else{
            throw new RuntimeException("No se puede eliminar el tipo de ingreso");
        }
    }

    @Override
    public void reactivate(Long id) {
        IncomeType incomeType = findById(id);
        incomeType.setActive(true);
        incomeTypeRepository.save(incomeType);
    }

}
