package com.example.portalbackend.domain.specifications;

import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.util.enumerate.ConvocationType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface ConvocationSpecifications {

    static Specification<Convocation> withDynamicFilters(String subject, Calendar start, Calendar end, ConvocationType type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (subject != null) {
                predicates.add(criteriaBuilder.like(root.get("subject"), "%" + subject + "%"));
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
                predicates.add(criteriaBuilder.between(root.get("date"), startClone, endDay));
            } else if (start != null & end != null) {

                Calendar startClone = (Calendar) start.clone();
                startClone.set(Calendar.HOUR_OF_DAY, 0);
                startClone.set(Calendar.MINUTE, 0);
                startClone.set(Calendar.SECOND, 0);

                Calendar endClone = (Calendar) end.clone();
                endClone.set(Calendar.HOUR_OF_DAY, 23);
                endClone.set(Calendar.MINUTE, 59);
                endClone.set(Calendar.SECOND, 59);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startClone));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endClone));
            }
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            predicates.add(criteriaBuilder.isTrue(root.get("active")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
