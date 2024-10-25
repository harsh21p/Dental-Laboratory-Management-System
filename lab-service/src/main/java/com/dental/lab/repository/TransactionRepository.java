package com.dental.lab.repository;

import com.dental.lab.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
  Page<Transaction> findAll(Specification<Transaction> transactionSpecification, Pageable pageable);
  @Query("SELECT t FROM Transaction t WHERE t.lab.id = :labId AND t.doctor.id = :doctorId ORDER BY t.transactionDate DESC, t.created DESC")
  Page<Transaction> findLastTransactionByLabAndDoctor(@Param("labId") String labId, @Param("doctorId") String doctorId, Pageable pageable);
}
