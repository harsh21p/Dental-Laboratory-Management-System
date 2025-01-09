package com.dental.doctor.config;

import com.dental.doctor.model.Doctor;
import com.dental.doctor.model.Lab;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DoctorSpecification {

    public static Specification<Doctor> filterByParameters(String labId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (labId != null) {
                Join<Doctor, Lab> join = root.join("labs");
                predicates.add(join.get("id").in(labId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
