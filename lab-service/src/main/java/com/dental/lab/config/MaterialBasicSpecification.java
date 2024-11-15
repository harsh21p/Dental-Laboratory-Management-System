package com.dental.lab.config;

import com.dental.lab.model.LabMaterial;
import com.dental.lab.model.Material;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MaterialBasicSpecification {

    public static Specification<Material> filterByParameters(String name) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name == null || name.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}


