package com.ds.studenterp.repository;

import com.ds.studenterp.entity.FeeStructure;
import com.ds.studenterp.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeeStructureRepository
        extends JpaRepository<FeeStructure, Long> {

    Optional<FeeStructure> findByCourse(Course course);
}