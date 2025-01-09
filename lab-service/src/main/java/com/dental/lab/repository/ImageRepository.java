package com.dental.lab.repository;

import com.dental.lab.model.Images;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ImageRepository extends JpaRepository<Images, String> {
    Page<Images> findAll(Specification<Images> imagesSpecification, Pageable pageable);

    Optional<Images> findByLabId(String id);
}
