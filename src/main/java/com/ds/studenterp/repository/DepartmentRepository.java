package com.ds.studenterp.repository;

import com.ds.studenterp.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    List<Department> findByActiveTrue();
}