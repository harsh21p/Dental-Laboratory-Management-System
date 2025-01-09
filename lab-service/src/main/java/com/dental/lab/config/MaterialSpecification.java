package com.dental.lab.config;

import com.dental.lab.model.LabMaterial;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MaterialSpecification {

    public static Specification<LabMaterial> filterByParameters(String name, String labId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (labId != null) {
                predicates.add(criteriaBuilder.equal(root.get("lab").get("id"), labId));
            }

            if (name != null) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}


