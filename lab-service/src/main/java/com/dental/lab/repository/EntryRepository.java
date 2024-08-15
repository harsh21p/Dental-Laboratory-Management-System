package com.dental.lab.repository;

import com.dental.lab.model.Entry;
import com.dental.lab.model.Lab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, String> {
    Page<Entry> findByLab(Optional<Lab> lab, Pageable pageable);
}
