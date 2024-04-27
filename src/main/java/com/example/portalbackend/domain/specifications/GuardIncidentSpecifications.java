package com.example.portalbackend.domain.specifications;

import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.entity.GuardIncident;
import com.example.portalbackend.domain.entity.IncidentType;
import com.example.portalbackend.util.enumerate.GuardIncidentStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Calendar;
import java.util.List;

public interface GuardIncidentSpecifications {

    static Specification<GuardIncident> withDynamicFilters(String subject, Calendar date, Guard guard, IncidentType type, GuardIncidentStatus status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new java.util.ArrayList<>();
            if (subject != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like( criteriaBuilder.lower(root.get("subject")), "%" + subject.toLowerCase() + "%")));
            }
            if (date != null) {
                Calendar start = (Calendar) date.clone();
                start.set(Calendar.HOUR_OF_DAY, 0);
                start.set(Calendar.MINUTE, 0);
                start.set(Calendar.SECOND, 0);

                Calendar end = (Calendar) date.clone();
                end.set(Calendar.HOUR_OF_DAY, 23);
                end.set(Calendar.MINUTE, 59);
                end.set(Calendar.SECOND, 59);
                predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get("date"), start, end)));
            }
            if (guard != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("guard"), guard)));
            }
            if (type != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("type"), type)));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
