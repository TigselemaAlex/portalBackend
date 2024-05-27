package com.example.portalbackend.domain.specifications;

import com.example.portalbackend.domain.entity.Penalty;
import com.example.portalbackend.domain.entity.PenaltyType;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.util.enumerate.PaidStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface PenaltySpecifications {
    static Specification<Penalty> withDynamicFilters(PenaltyType type, Calendar from, Calendar to, Residence residence, PaidStatus status){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }

            if (from != null && to == null) {
                Calendar fromClone = (Calendar) from.clone();
                fromClone.set(Calendar.HOUR_OF_DAY, 0);
                fromClone.set(Calendar.MINUTE, 0);
                fromClone.set(Calendar.SECOND, 0);

                Calendar toDay = (Calendar) from.clone();
                toDay.set(Calendar.HOUR_OF_DAY, 23);
                toDay.set(Calendar.MINUTE, 59);
                toDay.set(Calendar.SECOND, 59);
                predicates.add(criteriaBuilder.between(root.get("issueDate"), fromClone, toDay));
            }
            else if (from != null & to != null) {

                Calendar fromClone = (Calendar) from.clone();
                fromClone.set(Calendar.HOUR_OF_DAY, 0);
                fromClone.set(Calendar.MINUTE, 0);
                fromClone.set(Calendar.SECOND, 0);

                Calendar toClone = (Calendar) to.clone();
                toClone.set(Calendar.HOUR_OF_DAY, 23);
                toClone.set(Calendar.MINUTE, 59);
                toClone.set(Calendar.SECOND, 59);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("issueDate"), fromClone));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("issueDate"), toClone));
            }

            if (residence != null) {
                predicates.add(criteriaBuilder.equal(root.get("residence"), residence));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            predicates.add(criteriaBuilder.isTrue(root.get("active")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
