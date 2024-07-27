package com.dental.enrollment.repository;

import com.dental.enrollment.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
