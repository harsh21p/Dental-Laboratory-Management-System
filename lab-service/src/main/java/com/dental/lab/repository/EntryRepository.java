package com.dental.lab.repository;

import com.dental.lab.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EntryRepository extends JpaRepository<Entry, String> {
    Page<Entry> findAll(Specification<Entry> entrySpecification, Pageable pageable);
}
