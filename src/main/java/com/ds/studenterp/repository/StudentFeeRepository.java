package com.ds.studenterp.repository;

import com.ds.studenterp.entity.StudentFee;
import com.ds.studenterp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentFeeRepository
        extends JpaRepository<StudentFee, Long> {

    Optional<StudentFee> findByStudent(Student student);
}