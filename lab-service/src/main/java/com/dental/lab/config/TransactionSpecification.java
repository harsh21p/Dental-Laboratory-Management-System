package com.dental.lab.config;

import com.dental.lab.model.Entry;
import com.dental.lab.model.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionSpecification {

    public static Specification<Transaction> filterByParameters(Date startDate, Date endDate, String labId, String doctorId, String entryId, String invoiceId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (startDate != null && endDate != null) {
                predicates.add(criteriaBuilder.between(root.get("transactionDate"), startDate, endDate));
            }

            if (labId != null) {
                predicates.add(criteriaBuilder.equal(root.get("lab").get("id"), labId));
            }

            if (doctorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("doctor").get("id"), doctorId));
            }

            if (entryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("entry").get("id"), entryId));
            }
            if (invoiceId != null) {
                predicates.add(criteriaBuilder.equal(root.get("invoice").get("id"), invoiceId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
