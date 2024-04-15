package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityCreateData;
import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityUpdateData;
import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.entity.GuardActivity;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGuardActivityService {
    Long countByGuardAndStatus(Guard guard, GuardActivityStatus status);
    GuardActivity create(GuardActivityCreateData data);
    Page<GuardActivity> findAll(String subject, Pageable pageable);
    GuardActivity findById(Long id);
    GuardActivity update(Long id, GuardActivityUpdateData data);
    void delete(Long id);
}
