package com.dental.lab.config;

import com.dental.lab.model.Entry;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntrySpecification {

    public static Specification<Entry> filterByParameters(Date startDate, Date endDate, String labId, List<String> doctorIds) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (startDate != null && endDate != null) {
                predicates.add(criteriaBuilder.between(root.get("entryDate"), startDate, endDate));
            }

            if (labId != null) {
                predicates.add(criteriaBuilder.equal(root.get("lab").get("id"), labId));
            }

            if (doctorIds != null && !doctorIds.isEmpty()) {
                predicates.add(root.get("doctor").get("id").in(doctorIds));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
