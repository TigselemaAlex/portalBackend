package com.example.portalbackend.domain.specifications;

import com.example.portalbackend.domain.entity.Outcome;
import com.example.portalbackend.domain.entity.OutcomeType;
import com.example.portalbackend.domain.entity.Provider;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface OutcomeSpecifications {

    static Specification<Outcome> withDynamicFilters(OutcomeType type, String code, Provider provider, Calendar from , Calendar to){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }

            if (code != null) {
                predicates.add(criteriaBuilder.equal( criteriaBuilder.lower(root.get("code")), code.toLowerCase()));
            }

            if (provider != null) {
                predicates.add(criteriaBuilder.equal(root.get("provider"), provider));
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
                predicates.add(criteriaBuilder.between(root.get("paidDate"), fromClone, toDay));
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
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("paidDate"), fromClone));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("paidDate"), toClone));
            }

            predicates.add(criteriaBuilder.isTrue(root.get("active")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
