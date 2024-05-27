package com.example.portalbackend.domain.specifications;

import com.example.portalbackend.domain.entity.Income;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import com.example.portalbackend.util.enumerate.PaidStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface IncomeSpecifications {

    static Specification<Income> withDynamicFiltersMonthly(IncomeType type, Calendar from, Calendar to, Residence residence) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    criteriaBuilder.equal(root.get("type").get("period"), IncomeTypePeriod.MONTHLY)
            );
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

            if (residence != null) {
                predicates.add(criteriaBuilder.equal(root.get("residence"), residence));
            }



            predicates.add(criteriaBuilder.isTrue(root.get("active")));
            query.orderBy(criteriaBuilder.desc(root.get("paidDate")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    static Specification<Income> withDynamicFiltersCasual(IncomeType type, Calendar from, Calendar to, Residence residence) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    criteriaBuilder.equal(root.get("type").get("period"), IncomeTypePeriod.CASUAL)
            );
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
                predicates.add(criteriaBuilder.between(root.get("paidDate"), fromClone, toDay));
            } else if (from != null & to != null) {

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

            if (residence != null) {
                predicates.add(criteriaBuilder.equal(root.get("residence"), residence));
            }


            predicates.add(criteriaBuilder.isTrue(root.get("active")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
