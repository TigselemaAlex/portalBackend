package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.convocation.ConvocationCreateData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationUpdateData;
import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.util.enumerate.ConvocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IConvocationService {

    Convocation createConvocation(ConvocationCreateData data);

    Page<Convocation> findAll(String subject, Long start,Long end, ConvocationType type, Pageable pageable);

    Convocation findById(Long id);

    Convocation updateConvocation(Long id, ConvocationUpdateData data);

    void deleteConvocation(Long id);

    void finalizeConvocation(Long id);


}
