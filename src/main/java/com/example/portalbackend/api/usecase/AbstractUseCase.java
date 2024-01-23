package com.example.portalbackend.api.usecase;

import com.example.portalbackend.common.CustomResponseBuilder;

public abstract class AbstractUseCase {
    protected final CustomResponseBuilder customResponseBuilder;

    protected AbstractUseCase(CustomResponseBuilder customResponseBuilder) {
        this.customResponseBuilder = customResponseBuilder;
    }
}
