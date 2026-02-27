package com.ds.studenterp.repository;

import com.ds.studenterp.entity.Payment;
import com.ds.studenterp.entity.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentFee(StudentFee studentFee);
}