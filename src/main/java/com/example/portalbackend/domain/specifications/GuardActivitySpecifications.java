package com.example.portalbackend.domain.specifications;

import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.entity.GuardActivity;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Calendar;
import java.util.List;

public interface GuardActivitySpecifications {

    static Specification<GuardActivity> withDynamicFilters(String subject, Calendar start, Calendar end, GuardActivityStatus status, Guard guard, User createdBy) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new java.util.ArrayList<>();
            if (subject != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like( criteriaBuilder.lower(root.get("subject")), "%" + subject.toLowerCase() + "%")));
            }
            if (start != null && end == null) {
                Calendar startClone = (Calendar) start.clone();
                startClone.set(Calendar.HOUR_OF_DAY, 0);
                startClone.set(Calendar.MINUTE, 0);
                startClone.set(Calendar.SECOND, 0);

                Calendar endDay = (Calendar) start.clone();
                endDay.set(Calendar.HOUR_OF_DAY, 23);
                endDay.set(Calendar.MINUTE, 59);
                endDay.set(Calendar.SECOND, 59);
                predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get("startDate"), startClone, endDay)));
            }
            else if (start != null & end != null) {

                Calendar startClone = (Calendar) start.clone();
                startClone.set(Calendar.HOUR_OF_DAY, 0);
                startClone.set(Calendar.MINUTE, 0);
                startClone.set(Calendar.SECOND, 0);

                Calendar endClone = (Calendar) end.clone();
                endClone.set(Calendar.HOUR_OF_DAY, 23);
                endClone.set(Calendar.MINUTE, 59);
                endClone.set(Calendar.SECOND, 59);
                predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startClone)));
                predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endClone)));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
            }
            if (guard != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("guard"), guard)));
            }
            if (createdBy != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("createdBy"), createdBy)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
