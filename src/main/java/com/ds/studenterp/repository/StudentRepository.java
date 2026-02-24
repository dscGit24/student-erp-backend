package com.ds.studenterp.repository;

import com.ds.studenterp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByDeletedFalse();

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByAadharNumber(String aadharNumber);
}
