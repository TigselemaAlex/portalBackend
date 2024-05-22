package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.income_type.IncomeTypeCreateData;
import com.example.portalbackend.api.dto.request.income_type.IncomeTypeUpdateData;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.util.enumerate.IncomeTypePeriod;

import java.util.List;

public interface IIncomeTypeService {

    List<IncomeType> findAll();

    List<IncomeType> findAllActive();

    List<IncomeType> findAllActiveByPeriod(IncomeTypePeriod period);

    IncomeType findById(Long id);

    IncomeType save(IncomeTypeCreateData data);

    IncomeType update(IncomeTypeUpdateData data, Long id);

    void delete(Long id);

    void reactivate(Long id);
}
