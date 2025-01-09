package com.dental.lab.config;

import com.dental.lab.model.Entry;
import com.dental.lab.model.Invoice;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceSpecification {

    public static Specification<Invoice> filterByParameters(Date startDate, Date endDate, String labId, String doctorId, List<String> entryIds) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (startDate != null && endDate != null) {
                predicates.add(criteriaBuilder.between(root.get("invoiceDate"), startDate, endDate));
            }

            if (labId != null) {
                predicates.add(criteriaBuilder.equal(root.get("lab").get("id"), labId));
            }

            if (doctorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("doctor").get("id"), doctorId));
            }

            if (entryIds != null && !entryIds.isEmpty()) {
                Join<Invoice, Entry> entryJoin = root.join("entries");
                predicates.add(entryJoin.get("id").in(entryIds));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
